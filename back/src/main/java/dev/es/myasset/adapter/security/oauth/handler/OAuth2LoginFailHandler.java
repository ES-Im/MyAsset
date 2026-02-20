package dev.es.myasset.adapter.security.oauth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${app.frontend.base-url}")
    private String BASE_FRONT_URL;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException exception
    ) throws IOException {

        log.error("LOGIN FAILED : {}", exception.getClass().getSimpleName());

        getRedirectStrategy().sendRedirect(request, response, BASE_FRONT_URL+"/auth/sign-in?error=true");
    }

}
