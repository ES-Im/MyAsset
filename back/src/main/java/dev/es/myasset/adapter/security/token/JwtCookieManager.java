package dev.es.myasset.adapter.security.token;

import dev.es.myasset.application.exception.oauth.MissingTokenException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class JwtCookieManager {

    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, int maxAge) {

        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);

    }

    public static String getAccessTokenFromCookie(HttpServletRequest request) {
        if(request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("access_token")) {
                    return cookie.getValue();
                }
            }
        }

        throw new MissingTokenException();
    }

}
