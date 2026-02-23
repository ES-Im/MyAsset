package dev.es.myasset.application.exception.oauth;

import dev.es.myasset.application.exception.ErrorCode;

public class InActivatedAccount extends GlobalAuthException {

    public InActivatedAccount() {
        super(ErrorCode.INACTIVATED_ACCOUNT);
    }
}
