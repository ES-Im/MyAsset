package dev.es.myasset.application.exception.oauth;

public class UnauthenticatedContextException extends AbstractAuthException {
    public UnauthenticatedContextException() {
        super(AuthErrorCode.UNAUTHENTICATED_CONTEXT);
    }
}
