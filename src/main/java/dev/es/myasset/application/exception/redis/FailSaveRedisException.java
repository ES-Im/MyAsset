package dev.es.myasset.application.exception.redis;

import dev.es.myasset.application.exception.ErrorCode;

public class FailSaveRedisException extends GlobalRedisException {
    public FailSaveRedisException() {
        super(ErrorCode.FAIL_SAVE_REDIS);
    }
}
