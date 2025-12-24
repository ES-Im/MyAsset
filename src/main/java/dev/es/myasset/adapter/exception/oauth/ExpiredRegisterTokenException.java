package dev.es.myasset.adapter.exception.oauth;

public class ExpiredRegisterTokenException extends AbstractAuthException {

    public ExpiredRegisterTokenException() {
        super(AuthErrorCode.EXPIRED_REGISTER_TOKEN);
    }
}
