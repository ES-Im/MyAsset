package dev.es.myasset.application.exception.oauth;

import dev.es.myasset.application.exception.ErrorCode;

public class ExpiredAccessToken extends GlobalAuthException {

    public ExpiredAccessToken() {
        super(ErrorCode.EXPIRED_ACCESS_TOKEN);
    }
}
