package dev.es.myasset.adapter.security.oauth.handler;

import dev.es.myasset.adapter.security.token.JwtCookieManager;
import dev.es.myasset.adapter.security.token.JwtTokenUtil;
import dev.es.myasset.adapter.security.redis.RedisManager;
import dev.es.myasset.adapter.security.oauth.provider.GoogleOAuthUserInfo;
import dev.es.myasset.adapter.security.oauth.provider.KakaoOAuthUserInfo;
import dev.es.myasset.adapter.security.oauth.provider.NaverOAuthUserInfo;
import dev.es.myasset.application.UserService;
import dev.es.myasset.application.dto.OAuthSignupDto;
import dev.es.myasset.application.required.OAuth2UserInfo;
import dev.es.myasset.application.required.UserInfoRepository;
import dev.es.myasset.domain.user.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;


@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${app.frontend.base-url}")
    private String BASE_FRONT_URL;

    @Value("${spring.jwt.redirect.landing-path}")
    private String LANDING_PATH;

    private final JwtTokenUtil jwtTokenUtil;
    private final RedisManager redisManager;
    private final UserInfoRepository userInfoRepository;
    private final JwtCookieManager jwtCookieManager;
    private final UserService userService;

    @Override
    @Transactional
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) throws IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

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
                .orElseGet(() -> {
                        log.info("신규유저 - 회원등록");
                        return userService.assembleUserInfo(oAuthSignupDto, LocalDateTime.now());
                });

        String userKey = userInfo.getUserKey();

        String refreshToken = jwtTokenUtil.generateRefreshToken(userKey);
        jwtCookieManager.setRefreshCookie(refreshToken, response);
        redisManager.saveRefreshToken(userKey, refreshToken);
        
        // to-do : 프론트에서 accessToken받는 백엔드 호출 필요 -> back : /api/refresh 이용하면 될듯 - accesstoken이 리다이렉트에 의해 전달되지 않아 요청을 따로 콜백해야함
        getRedirectStrategy().sendRedirect(request, response, BASE_FRONT_URL + LANDING_PATH);
    }
}
