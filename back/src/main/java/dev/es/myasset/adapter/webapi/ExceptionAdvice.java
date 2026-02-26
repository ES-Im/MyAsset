package dev.es.myasset.adapter.webapi;

import dev.es.myasset.application.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({GlobalApplicationException.class, GlobalSecurityException.class})
    public ResponseEntity<ErrorResponse> handleGlobal(ErrorCodeCarrier e) {
        ErrorCode ec = e.getErrorCode();

        log.error("[Global 예외] code={}, message={}", ec.name(), ec.getMessage());

        log.info(ec.toString());

        return ResponseEntity.status(ec.getHttpStatus())
                .body(ErrorResponse.from(ec));

    }

}