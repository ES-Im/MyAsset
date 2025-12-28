package dev.es.myasset.application.exception.user;

import dev.es.myasset.application.exception.AbstractErrorException;
import dev.es.myasset.application.exception.ErrorCode;

public abstract class AbstractUserException extends AbstractErrorException {

    public AbstractUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
