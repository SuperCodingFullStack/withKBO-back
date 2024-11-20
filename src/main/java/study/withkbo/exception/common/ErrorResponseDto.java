package study.withkbo.exception.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponseDto {
    private String status;
    private String message;
    private ErrorDetails error;
    private LocalDateTime timestamp;

    public static ErrorResponseDto ERROR(String message, ErrorDetails error) {
        return ErrorResponseDto.builder()
                .status("failure")
                .message(message)
                .error(error)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Builder
    @Getter
    public static class ErrorDetails {

        private String code;
        private String details;
        private String path;

        public static ErrorDetails of(CommonError errorCode, String details, String path) {
            return ErrorDetails.builder()
                    .code(errorCode.getCode())
                    .details(details)
                    .path(path)
                    .build();
        }

        public static ErrorDetails of(CommonError errorCode, BindingResult bindingResult, String path) {
            return ErrorDetails.builder()
                    .code(errorCode.getCode())
                    .details(bindingResult.getFieldErrors().get(0).getDefaultMessage())
                    .path(path)
                    .build();
        }
    }

    public static ErrorResponseDto from(CommonError errorCode, String urlPath) {
        return ErrorResponseDto.builder()
                .status("failure") // 기본 상태를 "failure"로 설정
                .message(errorCode.name()) // ErrorCode의 메시지를 사용
                .error(ErrorDetails.of(errorCode, errorCode.getMessage(), urlPath)) // 기본 에러 세부사항 설정
                .timestamp(LocalDateTime.now()) // 현재 시간 설정
                .build();
    }
}
