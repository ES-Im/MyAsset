package dev.es.myasset.application.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class GlobalSecurityException extends AuthenticationException implements ErrorCodeCarrier {

    private final ErrorCode errorCode;

    public GlobalSecurityException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
