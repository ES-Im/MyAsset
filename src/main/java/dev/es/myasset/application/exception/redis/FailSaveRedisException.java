package dev.es.myasset.application.exception.redis;

public class FailSaveRedisException extends AbstractRedisException {
    public FailSaveRedisException() {
        super(RedisErrorCode.FAIL_SAVE_REDIS);
    }
}
