package dev.es.myasset.adapter.oauth;

import dev.es.myasset.application.required.OAuth2UserInfo;
import dev.es.myasset.application.required.UserRepository;
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
    private final UserRepository userRepository;

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

        // to - do : providerId를 통해 조회 후 기존유저/신규유저 분기 처리 - DTO 수정 및 userInfo 객체 생성 로직 전체적으로 바꾼 뒤 이어서 해야함.

        super.onAuthenticationSuccess(request, response, chain, authentication);
    }
}
