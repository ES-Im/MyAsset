package dev.es.myasset.adapter.security.filter;

import dev.es.myasset.adapter.security.auth.UserDetailService;
import dev.es.myasset.adapter.security.token.JwtTokenUtil;
import dev.es.myasset.application.exception.oauth.ExpiredTokenException;
import dev.es.myasset.application.exception.oauth.InActivatedAccount;
import dev.es.myasset.application.exception.oauth.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailService userDetailService;
    private final HandlerExceptionResolver exceptionResolver;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, UserDetailService userDetailService, @Qualifier(value = "handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailService = userDetailService;
        this.exceptionResolver = exceptionResolver;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {

        String accessToken = resolveToken(request);
        log.info("Access Token 확인, 요청 uri {}", request.getRequestURI());

        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }


        try {
            jwtTokenUtil.validateToken(accessToken);
            setAuthentication(accessToken);
        } catch (ExpiredTokenException e) {
            SecurityContextHolder.clearContext();
            log.error("JWT FILTER 예외 : expired access token, message = {}", e.getMessage());
            exceptionResolver.resolveException(request, response, null, e);
            return;
        } catch (InvalidTokenException e) {
            SecurityContextHolder.clearContext();
            log.error("JWT FILTER 예외 : invalid access token, message = {}", e.getMessage());
            exceptionResolver.resolveException(request, response, null, e);
            return;
        } catch (InActivatedAccount e) {
            SecurityContextHolder.clearContext();
            log.error("User STATUS != ACTIVATE : {}", e.getMessage());
            exceptionResolver.resolveException(request, response, null, e);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String accessToken) {
        String userKey = jwtTokenUtil.getSubject(accessToken);

        UserDetails userDetails = userDetailService.loadUserByUsername(userKey);

        if(!userDetails.isEnabled()) throw new InActivatedAccount();

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri = request.getRequestURI();
        return EXCLUDE_URL.stream().anyMatch(uri::contains);
    }

    private final List<String> EXCLUDE_URL = Arrays.asList(
            "/api/auth/logout", "/api/auth/refresh", "/api/auth/activateUser"
    );


}
