package dev.es.myasset.adapter.security.auth;


import dev.es.myasset.application.exception.redis.FailSaveRedisException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisManager {

    private final StringRedisTemplate redisTemplate;

    public void saveRefreshToken(String providerId, String refreshToken) {
        try {
            redisTemplate.opsForValue().set(providerId, refreshToken, 14, TimeUnit.DAYS);
        } catch (Exception e) {
            throw new FailSaveRedisException();
        }
    }

    public String getRefreshToken(String providerId) {
        return redisTemplate.opsForValue().get(providerId);
    }
}
