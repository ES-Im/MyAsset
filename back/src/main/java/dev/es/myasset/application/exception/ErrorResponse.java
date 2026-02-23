package dev.es.myasset.application.exception;

public record ErrorResponse(
    String name, String httpStatus, String message
) {
    public static ErrorResponse from(ErrorCode ec) {
        return new ErrorResponse(
                ec.name(), ec.getHttpStatus().toString(), ec.getMessage()
        );
    }
}

