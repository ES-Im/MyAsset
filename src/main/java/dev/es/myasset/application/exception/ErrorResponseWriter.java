package dev.es.myasset.application.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Slf4j
public class ErrorResponseWriter {

    // 공통 예외처리
    private void handleCustomException(HttpServletResponse response, String errorName, HttpStatus httpStatus, String message) throws IOException {

        response.setStatus(httpStatus.value());  // 상태 코드 설정

        String jsonResponse =
                String.format("{\"name\": \"%s\", \"httpStatus\": \"%s\", \"message\": \"%s\"}", errorName, httpStatus, message);

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);  // JSON 형식으로 예외 응답
    }

    // 예외 정보 반환
    public void errorResponseWriter(HttpServletResponse response, AbstractErrorException e) throws IOException {
        log.error("[JWT 예외 발생] 예외 발생 클래스: {}, 예외 발생Code: {}",
                e.getClass().getName(),
                e.getErrorCode().name());

        String errorName = e.getErrorCode().name();
        HttpStatus httpStatus = e.getErrorCode().getHttpStatus();
        String message = e.getErrorCode().getMessage();

        response.setStatus(httpStatus.value());  // 상태 코드 설정

        String jsonResponse =
                String.format("{\"name\": \"%s\", \"httpStatus\": \"%s\", \"message\": \"%s\"}", errorName, httpStatus, message);

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);  // JSON 형식으로 예외 응답
    }




}
