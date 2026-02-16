package dev.es.myasset.application.exception.oauth;

import dev.es.myasset.application.exception.ErrorCode;

public class ExpiredRegisterTokenException extends GlobalAuthException {

    public ExpiredRegisterTokenException() {
        super(ErrorCode.EXPIRED_REGISTER_TOKEN);
    }
}
