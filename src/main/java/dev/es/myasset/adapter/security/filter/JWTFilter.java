package dev.es.myasset.adapter.security.filter;

import dev.es.myasset.adapter.security.auth.UserAuthentication;
import dev.es.myasset.application.exception.oauth.InvalidTokenException;
import dev.es.myasset.adapter.security.token.JwtCookieManager;
import dev.es.myasset.adapter.security.token.JwtTokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenManager;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = JwtCookieManager.getAccessTokenFromCookie(request);
            jwtTokenManager.validateToken(accessToken);
            String providerId = jwtTokenManager.getProviderIdFromToken(accessToken);
            setAuthentication(request, providerId);
        } catch (InvalidTokenException e) {
            throw new InvalidTokenException();
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.equals("/") || path.startsWith("/login");
    }

    private void setAuthentication(HttpServletRequest request, String providerId) {
        UserAuthentication userAuth = new UserAuthentication(providerId, null, null);
        SecurityContextHolder.getContext().setAuthentication(userAuth);
    }

}
