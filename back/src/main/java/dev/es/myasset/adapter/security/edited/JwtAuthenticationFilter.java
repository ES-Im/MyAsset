package dev.es.myasset.adapter.security.edited;

import dev.es.myasset.application.exception.oauth.ExpiredTokenException;
import dev.es.myasset.application.exception.oauth.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailService userDetailService;

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
        } catch (InvalidTokenException e) {
            SecurityContextHolder.clearContext();
            log.error("JWT FILTER 예외 : invalid access token, message = {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String accessToken) {
        String userKey = jwtTokenUtil.getSubject(accessToken);

        UserDetails userDetails = userDetailService.loadUserByUsername(userKey);

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

}
