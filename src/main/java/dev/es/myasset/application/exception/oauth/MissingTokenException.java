package dev.es.myasset.application.exception.oauth;

import dev.es.myasset.application.exception.ErrorCode;

public class MissingTokenException extends AbstractAuthException {
    public MissingTokenException() {
        super(ErrorCode.MISSING_TOKEN);
    }
}
