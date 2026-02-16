package dev.es.myasset.application.exception.user;

import dev.es.myasset.application.exception.ErrorCode;

public class AgreementRequiredException extends GlobalUserException {
    public AgreementRequiredException() {
        super(ErrorCode.AGREEMENT_REQUIRED_EXCEPTION);
    }
}
