package dev.es.myasset.application.exception.oauth;

import dev.es.myasset.application.exception.GlobalSecurityException;
import dev.es.myasset.application.exception.ErrorCode;

public abstract class GlobalAuthException extends GlobalSecurityException {

    public GlobalAuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
