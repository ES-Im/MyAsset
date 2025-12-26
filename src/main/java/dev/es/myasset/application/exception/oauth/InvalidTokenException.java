package dev.es.myasset.application.exception.oauth;

public class InvalidTokenException extends AbstractAuthException {

    public InvalidTokenException() {
        super(AuthErrorCode.INVALID_REGISTER_TOKEN);
    }

}
