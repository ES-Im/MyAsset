package dev.es.myasset.adapter.security.oauth.handler;

import dev.es.myasset.adapter.security.oauth.provider.GoogleOAuthUserInfo;
import dev.es.myasset.adapter.security.oauth.provider.KakaoOAuthUserInfo;
import dev.es.myasset.adapter.security.oauth.provider.NaverOAuthUserInfo;
import dev.es.myasset.adapter.security.redis.RedisManager;
import dev.es.myasset.adapter.security.token.JwtCookieManager;
import dev.es.myasset.adapter.security.token.JwtTokenUtil;
import dev.es.myasset.application.dto.OAuthSignupDto;
import dev.es.myasset.application.exception.oauth.InActivatedAccount;
import dev.es.myasset.application.exception.user.NonExistAccount;
import dev.es.myasset.application.provided.UserRegister;
import dev.es.myasset.application.required.OAuth2UserInfo;
import dev.es.myasset.application.required.UserInfoRepository;
import dev.es.myasset.application.required.UserRepository;
import dev.es.myasset.domain.user.UserInfo;
import dev.es.myasset.domain.user.UserStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

import static dev.es.myasset.domain.user.UserStatus.ACTIVE;
import static dev.es.myasset.domain.user.UserStatus.WITHDRAWN;


@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    @Value("${app.frontend.base-url}")
    private String BASE_FRONT_URL;

    @Value("${spring.jwt.redirect.landing-path}")
    private String LANDING_PATH;

    private final JwtTokenUtil jwtTokenUtil;
    private final RedisManager redisManager;
    private final UserInfoRepository userInfoRepository;
    private final JwtCookieManager jwtCookieManager;
    private final UserRegister userRegister;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) throws IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        Object principal = authentication.getPrincipal();
        log.info("principal class={}", principal.getClass().getName());

        String provider = token.getAuthorizedClientRegistrationId();
        Map<String, Object> attributes = token.getPrincipal().getAttributes();

        OAuth2UserInfo oAuth2UserInfo = switch (provider) {
            case "kakao" -> new KakaoOAuthUserInfo(attributes);
            case "google" -> new GoogleOAuthUserInfo(attributes);
            case "naver" -> new NaverOAuthUserInfo(attributes);
            default -> throw new IllegalArgumentException("지원하지 않는 Provider");
        };

        OAuthSignupDto oAuthSignupDto = new OAuthSignupDto(
                oAuth2UserInfo.getProviderType(), oAuth2UserInfo.getProviderId()
                , oAuth2UserInfo.getEmail(), oAuth2UserInfo.getUsername()
        );

        UserInfo userInfo = userInfoRepository
                .findByProviderId(oAuth2UserInfo.getProviderId())
                .orElse(null);

        if (userInfo == null) {
            log.info("신규유저 - 회원등록");

            try {
                userInfo = userRegister.assembleUserInfo(oAuthSignupDto, Instant.now());
            } catch (DataIntegrityViolationException e) {
                log.info("중복 이메일, UserInfo 등록 실패");
                getRedirectStrategy().sendRedirect(request, response, BASE_FRONT_URL + "/auth/sign-in?error=duplicate");
                return;
            }
        }

        UserStatus userStatus = null;

        try {
            userStatus = userRepository.findUserStatusByUserKey(userInfo.getUserKey())
                    .orElseThrow(() -> new NonExistAccount());

            if (!userStatus.equals(ACTIVE)) throw new InActivatedAccount();


        } catch (InActivatedAccount e) {
            log.error("inactivate 예외", e);

            if(userStatus.equals(WITHDRAWN)) {
                getRedirectStrategy().sendRedirect(request, response, BASE_FRONT_URL + "/auth/sign-in?error=withdrawn-user");
                return;
            }

            // 유효시간이 짧은 activate Token을 만들어서 전달
            String activateToken = jwtTokenUtil.generateActivateToken(userInfo.getUserKey());
            jwtCookieManager.setActivateCookie(activateToken, response);

            getRedirectStrategy().sendRedirect(request, response, BASE_FRONT_URL + "/auth/inactivate-account");
            return;
        }

        String userKey = userInfo.getUserKey();

        String refreshToken = jwtTokenUtil.generateRefreshToken(userKey);
        jwtCookieManager.setRefreshCookie(refreshToken, response);
        redisManager.saveRefreshToken(userKey, refreshToken);
        
        getRedirectStrategy().sendRedirect(request, response, BASE_FRONT_URL + LANDING_PATH);
    }
}
