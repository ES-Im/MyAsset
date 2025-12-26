package dev.es.myasset.application.exception.oauth;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthErrorCode {
    EXPIRED_REGISTER_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_001", "Register token이 만료되었습니다."),
    INVALID_REGISTER_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_002", "Register token이 유효하지 않습니다."),
    MISSING_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_003", "인증 정보가 없습니다"),
    UNAUTHENTICATED_CONTEXT(HttpStatus.UNAUTHORIZED, "AUTH_004", "인증 정보가 없습니다"),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_005", "Refresh token이 만료되었습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_006", "Refresh token이 유효하지 않습니다");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    AuthErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

}
