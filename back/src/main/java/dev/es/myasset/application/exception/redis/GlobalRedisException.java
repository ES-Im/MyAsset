package dev.es.myasset.application.exception.redis;

import dev.es.myasset.application.exception.GlobalApplicationException;
import dev.es.myasset.application.exception.ErrorCode;

public abstract class GlobalRedisException extends GlobalApplicationException {

    public GlobalRedisException(ErrorCode errorCode) {
        super(errorCode);
    }
}
