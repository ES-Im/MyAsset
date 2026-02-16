package dev.es.myasset.application.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {

        if(ex instanceof GlobalSecurityException) {
            ErrorResponseWriter errorResponseWriter = new ErrorResponseWriter();
            errorResponseWriter.write(response, ((GlobalSecurityException) ex));
            return;
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
