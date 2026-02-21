package dev.es.myasset.adapter.security.redis;


import dev.es.myasset.adapter.security.token.ExpirationTimeProperties;
import dev.es.myasset.application.exception.oauth.InvalidRefreshTokenException;
import dev.es.myasset.application.exception.redis.FailDeleteRedisException;
import dev.es.myasset.application.exception.redis.FailSaveRedisException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/*
 * RefreshToken을 redis에 저장
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisManager {

    private final StringRedisTemplate redisTemplate;
    private final ExpirationTimeProperties expirationTimeProperties;

    public String addPrefixForRefreshToken(String userKey) {
        return "RT|" + userKey;
    }

    public void saveRefreshToken(String userKey, String refreshToken) {
        try {
            redisTemplate.opsForValue().set(
                    addPrefixForRefreshToken(userKey), refreshToken,
                    expirationTimeProperties.getRefreshTokenExpirationTime().toDays(), TimeUnit.DAYS);
            log.info("redis 저장완료");
        } catch (Exception e) {
            log.error("redis 저장실패");
            throw new FailSaveRedisException();
        }
    }

    public void validateRefreshToken(String userKey, String previousRefreshToken) {

        String stored = redisTemplate.opsForValue().get(userKey);
        if (stored == null) throw new InvalidRefreshTokenException();
        if (!stored.equals(previousRefreshToken)) throw new InvalidRefreshTokenException();

    }

    public void deleteRefreshToken(String userKey) {
        try {
            redisTemplate.delete(
                    addPrefixForRefreshToken(userKey)
            );
            log.info("redis 토큰 삭제 완료");
        } catch (Exception e) {
            log.error("redis 토큰 삭제 실패");
            throw new FailDeleteRedisException();
        }
    }
}
