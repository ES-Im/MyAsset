package dev.es.myasset.adapter.exception.oauth;

public class UnauthenticatedContextException extends AbstractAuthException {
    public UnauthenticatedContextException() {
        super(AuthErrorCode.UNAUTHENTICATED_CONTEXT);
    }
}
