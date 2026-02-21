package dev.es.myasset.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Auth Error Code
    UNAUTHENTICATED_CONTEXT(HttpStatus.UNAUTHORIZED, "AUTH_001", "인증 정보가 없습니다"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_002", "token이 유효하지 않습니다."),
    MISSING_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_003", "인증 정보가 없습니다"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_004", "Token이 만료되었습니다"),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_005", "Access token이 만료되었습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_006", "Access token이 유효하지 않습니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_007", "Refresh token이 만료되었습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_008", "Refresh token이 유효하지 않습니다"),

    // User Error Code
    AGREEMENT_REQUIRED_EXCEPTION(HttpStatus.BAD_REQUEST, "USER_001", "회원 가입을 위해 동의가 필요합니다"),
    DUPLICATED_EMAIL_EXCEPTION(HttpStatus.BAD_REQUEST, "USER_002", "이미 등록된 이메일입니다."),

    // Redis Error Code
    FAIL_SAVE_REDIS(HttpStatus.INTERNAL_SERVER_ERROR, "REDIS_001", "Redis 저장에 실패했습니다"),
    FAIL_CONNECTION_REDIS(HttpStatus.INTERNAL_SERVER_ERROR, "REDIS_002", "Redis 연결에 실패했습니다"),
    FAIL_DELETE_REDIS(HttpStatus.INTERNAL_SERVER_ERROR, "REDIS_003", "Redis 삭제에 실패했습니다");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}

