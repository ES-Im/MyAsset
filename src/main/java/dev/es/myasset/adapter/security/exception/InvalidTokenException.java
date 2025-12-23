package dev.es.myasset.adapter.security.exception;

public class InvalidTokenException extends AbstractAuthException {

    public InvalidTokenException() {
        super(AuthErrorCode.INVALID_TOKEN);
    }

}
