package dev.es.myasset.application.exception.user;

public class AgreementRequiredException extends AbstractUserException {
    public AgreementRequiredException() {
        super(UserErrorCode.AGREEMENT_REQUIRED_EXCEPTION);
    }
}
