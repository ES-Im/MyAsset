package dev.es.myasset.application.exception.oauth;

import dev.es.myasset.application.exception.ErrorCode;

public class InvalidAccessToken extends GlobalAuthException {

    public InvalidAccessToken() {
        super(ErrorCode.INVALID_ACCESS_TOKEN);
    }
}
