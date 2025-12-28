package dev.es.myasset.application.exception.redis;

import dev.es.myasset.application.exception.ErrorCode;

public class FailConnectionRedisException extends AbstractRedisException {
    public FailConnectionRedisException() {
        super(ErrorCode.FAIL_CONNECTION_REDIS);
    }
}
