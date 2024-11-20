package study.withkbo.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.exception.common.ErrorResponseDto;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // validation 에러가 발생할 경우
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponseDto> handleBindException(BindException e, HttpServletRequest request) {
        log.error("BindException occurred: {}", e.getBindingResult().getAllErrors());
        CommonError errorCode = CommonError.INVALID_INPUT;
        ErrorResponseDto.ErrorDetails errorDetails = ErrorResponseDto.ErrorDetails.of(
                errorCode, e.getBindingResult(), request.getRequestURI()
        );
        ErrorResponseDto errorResponse = ErrorResponseDto.ERROR(CommonError.INVALID_INPUT.toString(), errorDetails);

        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }

    // 지원하지 않는 HTTP method를 호출할 경우
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponseDto> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("HttpRequestMethodNotSupportedException occurred: {}", e.getMessage());
        CommonError errorCode = CommonError.METHOD_NOT_ALLOWED;
        ErrorResponseDto.ErrorDetails errorDetails = ErrorResponseDto.ErrorDetails.of(
                errorCode, e.getMessage(), request.getRequestURI()
        );
        ErrorResponseDto errorResponse = ErrorResponseDto.ERROR(CommonError.METHOD_NOT_ALLOWED.toString(), errorDetails);

        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }

    // 비즈니스 로직 실행 중 에러가 발생할 경우
    @ExceptionHandler(CommonException.class)
    protected ResponseEntity<ErrorResponseDto> handleBusinessException(CommonException e, HttpServletRequest request) {
        log.error("BusinessException occurred: {}", e.getMessage());
        ErrorResponseDto.ErrorDetails errorDetails = ErrorResponseDto.ErrorDetails.of(
                e.getCommonError(), e.getMessage(), request.getRequestURI());
        ErrorResponseDto errorResponse = ErrorResponseDto.ERROR(e.getCommonError().toString(), errorDetails);

        return ResponseEntity.status(e.getCommonError().getHttpStatus()).body(errorResponse);
    }
}
