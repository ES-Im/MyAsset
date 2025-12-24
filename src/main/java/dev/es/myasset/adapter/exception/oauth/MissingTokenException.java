package dev.es.myasset.adapter.exception.oauth;

public class MissingTokenException extends AbstractAuthException {
    public MissingTokenException() {
        super(AuthErrorCode.MISSING_TOKEN);
    }
}
