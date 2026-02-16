package dev.es.myasset.application.exception.user;

import dev.es.myasset.application.exception.GlobalApplicationException;
import dev.es.myasset.application.exception.ErrorCode;

public abstract class GlobalUserException extends GlobalApplicationException {

    public GlobalUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
