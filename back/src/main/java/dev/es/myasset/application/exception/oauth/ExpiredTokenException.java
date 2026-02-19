package dev.es.myasset.application.exception.oauth;

import dev.es.myasset.application.exception.ErrorCode;

public class ExpiredTokenException extends GlobalAuthException {
    public ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}
