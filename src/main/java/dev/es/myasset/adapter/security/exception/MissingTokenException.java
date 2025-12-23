package dev.es.myasset.adapter.security.exception;

public class MissingTokenException extends AbstractAuthException {
    public MissingTokenException() {
        super(AuthErrorCode.MISSING_TOKEN);
    }
}
