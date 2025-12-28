package dev.es.myasset.application.exception;

import lombok.Getter;

@Getter
public abstract class AbstractErrorException extends RuntimeException {

    private final ErrorCode errorCode;

    public AbstractErrorException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
