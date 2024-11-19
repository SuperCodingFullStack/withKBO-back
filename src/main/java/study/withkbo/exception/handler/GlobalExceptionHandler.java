package study.withkbo.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.withkbo.exception.common.ErrorResponse;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleMyPageException(CommonException exception) {

        CommonError error = exception.getCommonError();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(error.getStatus())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(error.getStatus()).body(errorResponse);
    }
}
