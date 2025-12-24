package dev.es.myasset.adapter.security;

import dev.es.myasset.adapter.security.token.JwtCookieManager;
import dev.es.myasset.adapter.security.token.JwtExpirationProperties;
import dev.es.myasset.adapter.security.token.JwtTokenManager;
import dev.es.myasset.adapter.security.oauth.GoogleOAuthUserInfo;
import dev.es.myasset.adapter.security.oauth.KakaoOAuthUserInfo;
import dev.es.myasset.adapter.security.oauth.NaverOAuthUserInfo;
import dev.es.myasset.application.required.OAuth2UserInfo;
import dev.es.myasset.application.required.UserRepository;
import dev.es.myasset.domain.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final JwtExpirationProperties expire = new JwtExpirationProperties();
    private final UserRepository userRepository;
    private final JwtTokenManager jwtTokenManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
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

        User existUser = userRepository.findByProviderId(providerId);

        if(existUser == null){
            log.info("신규유저");

            String registerToken = jwtTokenManager.generateRegisterToken(providerType, providerId, email, username);

            JwtCookieManager.setCookie(response, "register_token", registerToken, (int) expire.registerTokenExpirationTimeToMillis()/1000);

            getRedirectStrategy().sendRedirect(request, response, REDIRECT_URI_ONBOARDING);
        } else {
            log.info("기존유저");

            String accessToken = jwtTokenManager.generateRegisterToken(providerType, providerId, email, username);
            String refreshToken = jwtTokenManager.generateRegisterToken(providerType, providerId, email, username);

            JwtCookieManager.setCookie(response, "access_token", accessToken, (int) (expire.accessTokenExpirationTimeToMillis() * 1.5) /1000);
            JwtCookieManager.setCookie(response, "refresh_token", refreshToken, (int) expire.refreshTokenExpirationTimeToMillis()/1000);

            getRedirectStrategy().sendRedirect(request, response, REDIRECT_URI_BASE);
        }

        super.onAuthenticationSuccess(request, response, chain, authentication);
    }
}
