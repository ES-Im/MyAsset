package dev.es.myasset.adapter.exception.oauth;

import lombok.Getter;

@Getter
public abstract class AbstractAuthException extends RuntimeException {

    private final AuthErrorCode authErrorCode;

    public AbstractAuthException(AuthErrorCode authErrorCode) {
        super(authErrorCode.getMessage());
        this.authErrorCode = authErrorCode;
    }

}
