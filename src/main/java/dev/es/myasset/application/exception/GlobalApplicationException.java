package dev.es.myasset.application.exception;

import lombok.Getter;

@Getter
public class GlobalApplicationException extends RuntimeException implements ErrorCodeCarrier {

    private final ErrorCode errorCode;

    public GlobalApplicationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
