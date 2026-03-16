package dev.es.myasset.application.exception.user;

import dev.es.myasset.application.exception.ErrorCode;
import dev.es.myasset.application.exception.GlobalApplicationException;

public abstract class GlobalUserException extends GlobalApplicationException {

    public GlobalUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
