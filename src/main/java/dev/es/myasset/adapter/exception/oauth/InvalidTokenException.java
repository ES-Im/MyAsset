package dev.es.myasset.adapter.exception.oauth;

public class InvalidTokenException extends AbstractAuthException {

    public InvalidTokenException() {
        super(AuthErrorCode.INVALID_TOKEN);
    }

}
