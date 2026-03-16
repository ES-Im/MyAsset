package dev.es.myasset.adapter.security.token;


import dev.es.myasset.application.exception.oauth.ExpiredTokenException;
import dev.es.myasset.application.exception.oauth.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 * JWT 생성/파싱 컴포넌트
 */

@Slf4j
@Component
public class JwtTokenUtil {

    private final ExpirationTimeProperties expirationTimeProperties;
    private final SecretKey secretKey;

    public JwtTokenUtil(@Value("${spring.jwt.secret}") String secret,
                        ExpirationTimeProperties expirationTimeProperties) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationTimeProperties = expirationTimeProperties;
    }

    public Map<String, Object> getClaims(String token) {
        return new HashMap<>(parseToken(token));
    }

    public String getSubject(String token) {
        return parseToken(token).getSubject();
    }

    public String generateAccessToken(String userKey,
                                      String role) {

        Instant issuedAt = Instant.now();
        Instant expiredAt = issuedAt.plusMillis(expirationTimeProperties.accessTokenExpirationMillis());

        return Jwts.builder()
                .claim("tokenType", "ACCESS")
                .setSubject(userKey)
                .claim("role", role)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiredAt))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String userKey) {

        Instant issuedAt = Instant.now();
        Instant expiredAt = issuedAt.plusMillis(expirationTimeProperties.refreshTokenExpirationMillis());

        return Jwts.builder()
                .claim("tokenType", "REFRESH")
                .setSubject(userKey)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiredAt))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateActivateToken(String userKey) {

        Instant issuedAt = Instant.now();
        Instant expiredAt = issuedAt.plusMillis(expirationTimeProperties.activateTokenExpirationMillis());

        return Jwts.builder()
                .claim("tokenType", "ACTIVATE")
                .setSubject(userKey)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiredAt))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public void validateToken(String token) {
        parseToken(token);
    }

    private Claims parseToken(String token) {

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (ExpiredJwtException e) { throw new ExpiredTokenException(); }
        catch (JwtException e) { throw new InvalidTokenException(); }
    }


}
