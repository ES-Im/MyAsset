package dev.es.myasset.application.exception.oauth;

import dev.es.myasset.application.exception.ErrorCode;

public class ExpiredRegisterTokenException extends AbstractAuthException {

    public ExpiredRegisterTokenException() {
        super(ErrorCode.EXPIRED_REGISTER_TOKEN);
    }
}
