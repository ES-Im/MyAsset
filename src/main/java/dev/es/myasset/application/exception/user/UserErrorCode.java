package dev.es.myasset.application.exception.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode {
    AGREEMENT_REQUIRED_EXCEPTION(HttpStatus.BAD_REQUEST, "USER_001", "회원 가입을 위해 동의가 필요합니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    UserErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
