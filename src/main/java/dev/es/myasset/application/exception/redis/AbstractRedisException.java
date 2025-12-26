package dev.es.myasset.application.exception.redis;

import lombok.Getter;

@Getter
public class AbstractRedisException extends RuntimeException {

    private final RedisErrorCode userErrorCode;

    public AbstractRedisException(RedisErrorCode redisErrorCode) {
        super(redisErrorCode.getMessage());
        this.userErrorCode = redisErrorCode;
    }
}
