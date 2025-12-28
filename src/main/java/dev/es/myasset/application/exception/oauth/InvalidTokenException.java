package dev.es.myasset.application.exception.oauth;

import dev.es.myasset.application.exception.ErrorCode;

public class InvalidTokenException extends AbstractAuthException {

    public InvalidTokenException() {
        super(ErrorCode.INVALID_REGISTER_TOKEN);
    }

}
