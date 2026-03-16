package dev.es.myasset.application.exception.redis;

import dev.es.myasset.application.exception.ErrorCode;
import dev.es.myasset.application.exception.GlobalApplicationException;

public abstract class GlobalRedisException extends GlobalApplicationException {

    public GlobalRedisException(ErrorCode errorCode) {
        super(errorCode);
    }
}
