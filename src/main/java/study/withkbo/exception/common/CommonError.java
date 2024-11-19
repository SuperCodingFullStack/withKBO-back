package study.withkbo.exception.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CommonError {


    //공통 - 코드 접두사 (E)
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"E-001","잘못된 요청입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN,"E-002", "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND,"E-003","요청한 리소스를 찾을 수 없습니다"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "E-004","허용되지 않은 메소드 입니다."),
    CONFLICT(HttpStatus.CONFLICT, "E-005","데이터 충돌이 발생 했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"E-006","서버 내부 오류입니다."),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE,"E-007","해당 서비스를 이용할 수 없습니다."),
    FILE_NOT_FOUND(HttpStatus.BAD_REQUEST,"E-008", "파일이 비어있습니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "E-009", "입력값 검증에 실패했습니다."),

    //게임 - 코드 접두사(G)
    GAME_NOT_FOUND(HttpStatus.BAD_REQUEST, "G-001","해당 월 경기 정보가 없습니다."),
    CRAWLING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G-002","웹 크롤링 중 오류가 발생했습니다."),

    //팀 - 코드 접두사(T)
    NO_TEAM_INFO(HttpStatus.BAD_REQUEST, "T-001","해당 팀 정보가 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


}