package dev.es.myasset.application.exception.oauth;

public class MissingTokenException extends AbstractAuthException {
    public MissingTokenException() {
        super(AuthErrorCode.MISSING_TOKEN);
    }
}
