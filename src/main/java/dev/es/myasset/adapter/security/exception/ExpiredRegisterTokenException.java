package dev.es.myasset.adapter.security.exception;

public class ExpiredRegisterTokenException extends AbstractAuthException {

    public ExpiredRegisterTokenException() {
        super(AuthErrorCode.EXPIRED_REGISTER_TOKEN);
    }
}
