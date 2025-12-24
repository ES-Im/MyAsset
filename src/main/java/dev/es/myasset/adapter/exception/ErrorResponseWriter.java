package dev.es.myasset.adapter.exception;

import dev.es.myasset.adapter.exception.oauth.AbstractAuthException;
import dev.es.myasset.adapter.exception.user.AbstractUserException;
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

    // Auth 예외
    public void handleAuthCustomException(HttpServletResponse response, AbstractAuthException e) throws IOException {
        log.error("[JWT 예외 발생] 예외 발생 클래스: {}, 예외 발생Code: {}",
                e.getClass().getName(),
                e.getAuthErrorCode().name());

        String errorName = e.getAuthErrorCode().name();
        HttpStatus httpStatus = e.getAuthErrorCode().getHttpStatus();
        String message = e.getAuthErrorCode().getMessage();

        handleCustomException(response, errorName, httpStatus, message);
    }

    // User 예외
    public void handleUserCustomException(HttpServletResponse response, AbstractUserException e) throws IOException {
        log.error("[User 예외 발생] 예외 발생 클래스: {}, 예외 발생Code: {}",
                e.getClass().getName(),
                e.getUserErrorCode().name());

        String errorName = e.getUserErrorCode().name();
        HttpStatus httpStatus = e.getUserErrorCode().getHttpStatus();
        String message = e.getUserErrorCode().getMessage();

        handleCustomException(response, errorName, httpStatus, message);
    }


}
