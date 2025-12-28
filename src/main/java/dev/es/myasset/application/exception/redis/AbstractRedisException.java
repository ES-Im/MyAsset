package dev.es.myasset.application.exception.redis;

import dev.es.myasset.application.exception.AbstractErrorException;
import dev.es.myasset.application.exception.ErrorCode;

public abstract class AbstractRedisException extends AbstractErrorException {

    public AbstractRedisException(ErrorCode errorCode) {
        super(errorCode);
    }
}
