package dev.es.myasset.application.exception;

import lombok.Getter;

import static java.util.Objects.requireNonNull;

@Getter
public class GlobalApplicationException extends RuntimeException implements ErrorCodeCarrier {

    private final ErrorCode errorCode;

    public GlobalApplicationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = requireNonNull(errorCode);
    }

    @Override
    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

}
