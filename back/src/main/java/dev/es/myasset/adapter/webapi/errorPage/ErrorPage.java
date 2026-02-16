package dev.es.myasset.adapter.webapi.errorPage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@RestControllerAdvice
@RequestMapping
public class ErrorPage extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(GlobalApplicationException.class)
//    public ResponseEntity<ErrorResponse> handleException(GlobalApplicationException error, HttpServletResponse response) throws IOException {
//
//        ErrorResponseWriter responseWriter = new ErrorResponseWriter();
//        responseWriter.write(response, error);
//
//        return null;
//    }


}
