package dev.es.myasset.adapter.security.exception;

public abstract class AbstractAuthException extends RuntimeException {
    private final AuthErrorCode authErrorCode;

    public AbstractAuthException(AuthErrorCode authErrorCode) {
        super(authErrorCode.getMessage());
        this.authErrorCode = authErrorCode;
    }

}
