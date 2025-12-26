package dev.es.myasset.application.exception.user;

import lombok.Getter;

@Getter
public class AbstractUserException extends RuntimeException {

    private final UserErrorCode userErrorCode;

    public AbstractUserException(UserErrorCode userErrorCode) {
        super(userErrorCode.getMessage());
        this.userErrorCode = userErrorCode;
    }
}
