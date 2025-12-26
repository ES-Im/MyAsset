package dev.es.myasset.adapter.security.auth;

import dev.es.myasset.adapter.security.token.JwtCookieManager;
import dev.es.myasset.adapter.security.token.JwtExpirationProperties;
import dev.es.myasset.adapter.security.token.JwtTokenManager;
import dev.es.myasset.application.exception.oauth.ExpiredRefreshTokenException;
import dev.es.myasset.application.exception.oauth.InvalidRefreshTokenException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class JwtTokenManagement {

    private final JwtTokenManager jwtTokenManager;
    private final JwtCookieManager jwtCookieManager;
    private final RedisManager redisManager;
    private final JwtExpirationProperties expire;

    public Map<String, String> parseToken(String token) {
        HashMap<String, String> tokenInfo = new HashMap<>();
        tokenInfo.put("providerType", jwtTokenManager.getProviderTypeFromToken(token));
        tokenInfo.put("providerId", jwtTokenManager.getProviderIdFromToken(token));
        tokenInfo.put("email", jwtTokenManager.getProviderEmailFromToken(token));
        tokenInfo.put("username", jwtTokenManager.getProviderUsernameFromToken(token));

        return tokenInfo;
    }

    @Transactional
    public void reIssueToken(String refreshToken, HttpServletResponse response) {
        if(refreshToken == null){
            throw new ExpiredRefreshTokenException();
        }

        String providerId = jwtTokenManager.getProviderIdFromToken(refreshToken);

        String storedRefreshTokenInRedis = redisManager.getRefreshToken(providerId);
        if(!storedRefreshTokenInRedis.equals(refreshToken)){
            throw new InvalidRefreshTokenException();
        }

        Map<String, String> refreshTokenMap = parseToken(refreshToken);

        String newAccessToken = jwtTokenManager.generateToken(
                refreshTokenMap.get("providerType"),
                refreshTokenMap.get("providerId"),
                refreshTokenMap.get("email"),
                refreshTokenMap.get("username"),
                expire.accessTokenExpirationTimeToMillis()
        );

        String newRefreshToken = jwtTokenManager.generateToken(
                refreshTokenMap.get("providerType"),
                refreshTokenMap.get("providerId"),
                refreshTokenMap.get("email"),
                refreshTokenMap.get("username"),
                expire.refreshTokenExpirationTimeToMillis()
        );

        jwtCookieManager.setCookie(
                response, "access_token",
                newAccessToken, expire.accessCookieExpirationTimeToSecond()
        );

        jwtCookieManager.setCookie(
                response, "fresh_token",
                newRefreshToken, expire.refreshCookieExpirationTimeToSecond()
        );
    }

}
