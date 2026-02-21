package dev.es.myasset.application.exception.redis;

import dev.es.myasset.application.exception.ErrorCode;

public class FailDeleteRedisException extends GlobalRedisException {
    public FailDeleteRedisException() {
        super(ErrorCode.FAIL_DELETE_REDIS);
    }
}
