package dev.es.myasset.adapter.security.token;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
/*
 * Jwt 관련 토큰 * accessToken, refreshToken *을 관리 
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtCookieManager {

    private final ExpirationTimeProperties expirationTimeProperties;

    public void setRefreshCookie(String refreshToken, HttpServletResponse response) {

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(expirationTimeProperties.refreshCookieExpirationSeconds());
        cookie.setSecure(false);    // to-do : HTTPS 변경시 true로 변경

        response.addCookie(cookie);

    }

    public void removeRefreshCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        log.info("refresh cookie 삭제 시작");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        log.info("refresh cookie 삭제 성공");
    }

}
