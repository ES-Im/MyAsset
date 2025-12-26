package dev.es.myasset.application.exception.redis;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum RedisErrorCode {
    FAIL_SAVE_REDIS(HttpStatus.INTERNAL_SERVER_ERROR, "REDIS_001", "Redis 저장에 실패했습니다"),
    FAIL_CONNECTION_REDIS(HttpStatus.INTERNAL_SERVER_ERROR, "REDIS_002", "Redis 연결에 실패했습니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    RedisErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
