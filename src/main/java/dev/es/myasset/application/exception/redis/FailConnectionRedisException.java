package dev.es.myasset.application.exception.redis;

public class FailConnectionRedisException extends AbstractRedisException {
    public FailConnectionRedisException() {
        super(RedisErrorCode.FAIL_CONNECTION_REDIS);
    }
}
