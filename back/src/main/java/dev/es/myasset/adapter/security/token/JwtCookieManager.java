package dev.es.myasset.adapter.security.token;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
/*
 * refreshToken 정보를 cookie에 셋팅
 */
@Component
@RequiredArgsConstructor
public class JwtCookieManager {

    private final ExpirationTimeProperties expirationTimeProperties;

    public void setRefreshCookie(String refreshToken, HttpServletResponse response) {

        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(expirationTimeProperties.refreshCookieExpirationSeconds());
        cookie.setSecure(false);    // to-do : HTTPS 변경시 true로 변경

        response.addCookie(cookie);

    }

}
