package study.withkbo.exception.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CommonError implements ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND,"요청한 리소스를 찾을 수 없습니다"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메소드 입니다."),
    CONFLICT(HttpStatus.CONFLICT, "데이터 충돌이 발생 했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"서버 내부 오류입니다."),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE,"해당 서비스를 이용할 수 없습니다."),

    FILE_NOT_FOUND(HttpStatus.BAD_REQUEST, "파일이 비어있습니다."),
    GAME_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 월 경기 정보가 없습니다."),
    CRAWLING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "웹 크롤링 중 오류가 발생했습니다."),
    NO_TEAM_INFO(HttpStatus.BAD_REQUEST, "해당 팀 정보가 없습니다.");

    private final HttpStatus status;
    private final String message;

}
