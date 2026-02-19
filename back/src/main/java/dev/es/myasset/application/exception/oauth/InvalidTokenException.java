package dev.es.myasset.application.exception.oauth;

import dev.es.myasset.application.exception.ErrorCode;

public class InvalidTokenException extends GlobalAuthException {

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }

}
