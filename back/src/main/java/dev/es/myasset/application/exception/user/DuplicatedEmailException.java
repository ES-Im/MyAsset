package dev.es.myasset.application.exception.user;

import dev.es.myasset.application.exception.ErrorCode;

public class DuplicatedEmailException extends GlobalUserException {
    public DuplicatedEmailException() {
        super(ErrorCode.DUPLICATED_EMAIL_EXCEPTION);
    }
}
