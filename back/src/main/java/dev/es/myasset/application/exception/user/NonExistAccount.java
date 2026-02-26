package dev.es.myasset.application.exception.user;

import dev.es.myasset.application.exception.ErrorCode;

public class NonExistAccount extends GlobalUserException {
    public NonExistAccount() {
        super(ErrorCode.NON_EXIST_ACCOUNT);
    }
}
