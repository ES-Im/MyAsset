package dev.es.myasset.adapter.security.handler;

import dev.es.myasset.adapter.security.edited.RedisManager;
import dev.es.myasset.adapter.security.edited.JwtCookieManager;
import dev.es.myasset.adapter.security.edited.ExpirationTimeProperties;
import dev.es.myasset.adapter.security.edited.JwtTokenUtil;
import dev.es.myasset.adapter.security.oauth.GoogleOAuthUserInfo;
import dev.es.myasset.adapter.security.oauth.KakaoOAuthUserInfo;
import dev.es.myasset.adapter.security.oauth.NaverOAuthUserInfo;
import dev.es.myasset.application.UserService;
import dev.es.myasset.application.required.OAuth2UserInfo;
import dev.es.myasset.application.required.UserInfoRepository;
import dev.es.myasset.domain.user.User;
import dev.es.myasset.domain.user.UserInfo;
import dev.es.myasset.domain.user.UserRole;
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
import java.util.HashMap;
import java.util.Map;

import static dev.es.myasset.domain.user.UserRole.ROLE_USER;


@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${spring.jwt.redirect.base}")
    private String REDIRECT_URI_BASE;

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

        UserInfo userInfo = userInfoRepository
                .findByProviderId(oAuth2UserInfo.getProviderId())
                .orElseGet(() -> {
                        log.info("신규유저 - 회원등록");
                        return userService.registerFromOAuth(oAuth2UserInfo);
                });

        String userKey = userInfo.getUserKey();

        String accessToken = jwtTokenUtil.generateAccessToken(userKey, userInfo.getUser().getRole().name());
        String refreshToken = jwtTokenUtil.generateRefreshToken(userKey);

        response.setHeader("Authorization", "Bearer " + accessToken);

        jwtCookieManager.setRefreshCookie(refreshToken, response);
        redisManager.saveRefreshToken(userKey, refreshToken);

        getRedirectStrategy().sendRedirect(request, response, REDIRECT_URI_BASE);
    }
}
