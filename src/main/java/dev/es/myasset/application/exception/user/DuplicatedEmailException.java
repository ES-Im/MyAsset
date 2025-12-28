package dev.es.myasset.application.exception.user;

import dev.es.myasset.application.exception.ErrorCode;

public class DuplicatedEmailException extends AbstractUserException {
    public DuplicatedEmailException() {
        super(ErrorCode.DUPLICATED_EMAIL_EXCEPTION);
    }
}
