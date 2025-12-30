package dev.es.myasset.application.exception.oauth;

import dev.es.myasset.application.exception.ErrorCode;

public class UnauthenticatedContextException extends GlobalAuthException {
    public UnauthenticatedContextException() {
        super(ErrorCode.UNAUTHENTICATED_CONTEXT);
    }
}
