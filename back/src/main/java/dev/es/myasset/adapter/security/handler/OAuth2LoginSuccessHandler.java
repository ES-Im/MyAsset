package dev.es.myasset.adapter.security.handler;

import dev.es.myasset.adapter.security.auth.RedisManager;
import dev.es.myasset.adapter.security.token.JwtCookieManager;
import dev.es.myasset.adapter.security.token.JwtExpirationProperties;
import dev.es.myasset.adapter.security.token.JwtTokenManager;
import dev.es.myasset.adapter.security.oauth.GoogleOAuthUserInfo;
import dev.es.myasset.adapter.security.oauth.KakaoOAuthUserInfo;
import dev.es.myasset.adapter.security.oauth.NaverOAuthUserInfo;
import dev.es.myasset.application.required.OAuth2UserInfo;
import dev.es.myasset.application.required.UserInfoRepository;
import dev.es.myasset.domain.user.UserInfo;
import jakarta.servlet.ServletException;
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
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${spring.jwt.redirect.onboarding}")
    private String REDIRECT_URI_ONBOARDING;

    @Value("${spring.jwt.redirect.base}")
    private String REDIRECT_URI_BASE;

    private OAuth2UserInfo oAuth2UserInfo = null;
    private final JwtExpirationProperties expire;
    private final JwtTokenManager jwtTokenManager;
    private final JwtExpirationProperties jwtExpirationProperties;
    private final RedisManager redisManager;
    private final UserInfoRepository userInfoRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        final String provider = token.getAuthorizedClientRegistrationId();
        final Map<String, Object> attributes = token.getPrincipal().getAttributes();

        switch (provider) {
            case "kakao" -> oAuth2UserInfo = new KakaoOAuthUserInfo(attributes);
            case "google" -> oAuth2UserInfo = new GoogleOAuthUserInfo(attributes);
            case "naver" -> oAuth2UserInfo = new NaverOAuthUserInfo(attributes);
        }

        String providerType = oAuth2UserInfo.getProviderType();
        String providerId = oAuth2UserInfo.getProviderId();
        String email =  oAuth2UserInfo.getEmail();
        String username = oAuth2UserInfo.getUsername();

        UserInfo existUser = userInfoRepository.findByProviderId(providerId);

        if(existUser == null){
            log.info("신규유저");

            String registerToken = jwtTokenManager.generateToken(providerType, providerId, email, username, jwtExpirationProperties.registerTokenExpirationTimeToMillis());

            JwtCookieManager.setCookie(
                    response, "register_token",
                    registerToken, expire.registerCookieExpirationTimeToSecond()
                    );
            log.info("register token 발행 및 쿠키설정 - providerType = {}, providerId = {}", providerType, providerId);

            log.info("REDIRECT_URI_ONBOARDING으로 redirect");
            getRedirectStrategy().sendRedirect(request, response, REDIRECT_URI_ONBOARDING);
        } else {
            log.info("기존유저");

            String accessToken = jwtTokenManager.generateToken(providerType, providerId, email, username, jwtExpirationProperties.accessTokenExpirationTimeToMillis());
            String refreshToken = jwtTokenManager.generateToken(providerType, providerId, email, username, jwtExpirationProperties.refreshTokenExpirationTimeToMillis());

            JwtCookieManager.setCookie(
                    response, "access_token",
                    accessToken, expire.accessCookieExpirationTimeToSecond());
            JwtCookieManager.setCookie(
                    response, "refresh_token",
                    refreshToken, expire.refreshCookieExpirationTimeToSecond());
            log.info("access, refresh token 발행 및 쿠키설정 - providerType = {}, providerId = {}", providerType, providerId);

            redisManager.saveRefreshToken(providerId, refreshToken);

            log.info("refresh token, redis 설정 완료 REDIRECT_URI_BASE으로 redirect");
            getRedirectStrategy().sendRedirect(request, response, REDIRECT_URI_BASE);
        }

    }
}
