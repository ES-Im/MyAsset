package dev.es.myasset.adapter.security.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
enum AuthErrorCode {
    EXPIRED_REGISTER_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_001", "Register token이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_002", "Register token이 유효하지 않습니다."),
    MISSING_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_003", "인증 정보가 없습니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    AuthErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

}
