package dev.es.myasset.application.exception.oauth;

import dev.es.myasset.application.exception.ErrorCode;
import dev.es.myasset.application.exception.GlobalSecurityException;

public abstract class GlobalAuthException extends GlobalSecurityException {

    public GlobalAuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
