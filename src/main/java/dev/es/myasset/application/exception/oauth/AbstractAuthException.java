package dev.es.myasset.application.exception.oauth;

import dev.es.myasset.application.exception.AbstractErrorException;
import dev.es.myasset.application.exception.ErrorCode;

public abstract class AbstractAuthException extends AbstractErrorException {

    public AbstractAuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
