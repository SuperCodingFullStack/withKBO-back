package study.withkbo.exception.common;

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
    GAME_NOT_FOUND(HttpStatus.BAD_REQUEST, "G-001","해당 경기 정보가 없습니다."),
    CRAWLING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G-002","웹 크롤링 중 오류가 발생했습니다."),

    //팀 - 코드 접두사(T)
    NO_TEAM_INFO(HttpStatus.BAD_REQUEST, "T-001","해당 팀 정보가 없습니다."),

    //알림 - 코드 접두사(N)
    NOTIFICATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "N-001","해당 알림이 비어 있습니다."),

    //파티 - 코드접두사(P)
    PARTY_REQUEST_AlREADY_SEND(HttpStatus.BAD_REQUEST, "P-001","참가 신청을 이미 완료한 파티입니다."),
    PARTY_NOT_FOUND(HttpStatus.NOT_FOUND,"P-002","해당하는 파티가 존재하지 않습니다."),


    //친구 - 코드 접두사(F)
    FRIEND_REQUEST_ALREADY_SEND(HttpStatus.BAD_REQUEST,"F-001", "친구 신청을 이미 보낸 사용자입니다."),
    FRIEND_NOT_FOUND(HttpStatus.NOT_FOUND, "F-002", "해당하는 친구가 없습니다."),
    FRIEND_ALREADY_BLOCK(HttpStatus.BAD_REQUEST, "F-003","이미 차단한 사용자입니다."),
    FRIEND_NON_EXISTENT(HttpStatus.BAD_REQUEST, "F-004","친구가 존재하지 않습니다."),

    //유저(사용지) - 코드 접두사(U)
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U-001", "해당하는 사용자가 존재하지 않습니다."),
    USER_ALREADY_EXIST_USERNAME(HttpStatus.BAD_REQUEST, "U-002","해당 아이디를 사용하는 유저가 이미 존재합니다."),
    USER_ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "U-003","해당 이메일을 사용하는 유저가 이미 존재합니다."),
    USER_LOGIN_FAILED(HttpStatus.BAD_REQUEST, "U-004", "로그인에 실패하셨습니다."),
    USER_PASSWORD_WRONG(HttpStatus.BAD_REQUEST, "U-005", "비밀번호가 일치하지 않습니다."),

    //채팅방 - 코드 접두사(C)
    CHAT_ROOM_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,"C-001","이미 존재하는 채팅방입니다."),

    //웹소켓  - 코드 접두사(W)
    WEB_SOCKET_CONNECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "W-001", "웹소켓 연결 오류가 발생하였습니다."),
    WEB_SOCKET_MESSAGE_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "W-002", "메시지 전송 중 오류가 발생하였습니다"),
    WEB_SOCKET_MESSAGE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "W-003", "메시지 처리 중 오류가 발생하였습니다."),
    WEB_SOCKET_CLOSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "W-004", "웹소켓 종료 중 오류가 발생하였습니다."),
    WEB_SOCKET_CONNECT_SESSION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"W-005","웹소켓 세션 연결 에러가 발생하였습니다."),
    WEB_SOCKET_TRANSPORT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"W-006","웹소켓 네트워크 전송 오류가 발생하였습니다."),
    WEB_SOCKET_STOMP_PROTOCOL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"W-007","웹소켓 STOMP PROTOCOL 오류가 발생하였습니다."),
    WEB_SOCKET_MAPPING_SESSION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"W-007","STOMP session과 WebSocket session이 다릅니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


}
