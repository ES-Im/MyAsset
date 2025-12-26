package dev.es.myasset.adapter.security.token;


import dev.es.myasset.application.exception.oauth.ExpiredRegisterTokenException;
import dev.es.myasset.application.exception.oauth.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenManager {

    private final SecretKey secretKey;
    private final JwtExpirationProperties jwtExpirationProperties;

    public JwtTokenManager(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.jwtExpirationProperties = new JwtExpirationProperties();
    }

    public String generateToken(String providerType, String providerId, String email, String username, long expriedMillis) {
        long now = System.currentTimeMillis();
        Date currentDate = new Date(now);
        Date expirationDate = new Date(now + jwtExpirationProperties.registerTokenExpirationTimeToMillis());

        // payload
        Map<String, Object> claims = new HashMap<>();
        claims.put("providerType", providerType);
        claims.put("providerId", providerId);
        claims.put("email", email);
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public void validateToken(String token) {
        tokenParser(token);
    }

    public String getProviderTypeFromToken(String token) {
        return tokenParser(token).get("providerType").toString();
    }

    public String getProviderIdFromToken(String token) {
        return tokenParser(token).get("providerId").toString();
    }

    public String getProviderUsernameFromToken(String token) {
        return tokenParser(token).get("username").toString();
    }

    public String getProviderEmailFromToken(String token) {
        return tokenParser(token).get("email").toString();
    }

    private Claims tokenParser(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch(ExpiredJwtException e) {
            throw new ExpiredRegisterTokenException();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }
}
