package study.withkbo.server.handler;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ChatWebSocketHandler extends TextWebSocketHandler {
    // 웹소켓 서버 측 연결 처리
    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final Map<String, String> stompToWebSocketMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        try {
            sessions.put(session.getId(), session);
            log.info("WebSocket 연결 성공: sessionId = {}", session.getId());
        } catch (Exception e) {
            log.error("WebSocket 연결 오류 : {} ",e.getMessage(), e);
            throw new CommonException(CommonError.WEB_SOCKET_CONNECT_ERROR);
        }
    }

    // 브로드캐스트
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("handleTextMessage: " + session.getId() + sessions);
        System.out.println("handleTextMessage: " + message.getPayload());
        // 메시지 처리
        try {
            sessions.forEach((key, value) -> {
                System.out.println("key : "+ key + " value : " + value);
                if(!key.equals(session.getId())){ // 같은 아이디가 아니면 메시지를 전달
                    try {
                        value.sendMessage(message);
                    } catch (IOException e) {
                        log.error("Error closing WebSocket session", e);
                        throw new CommonException(CommonError.WEB_SOCKET_MESSAGE_SEND_ERROR);
                    }
                }
            });
        } catch (Exception e) {
            throw new CommonException(CommonError.WEB_SOCKET_MESSAGE_ERROR);
        }
    }

    // {roomName}에 브로드캐스트
    public void sendMessageToRoom(String roomName, String message) {
        if (roomName == null || roomName.isEmpty() || message == null) {
            throw new CommonException(CommonError.INVALID_INPUT);
        }
        // stompToWebSocketMap을 순회하며 해당 roomName에 속한 세션만 필터링
        sessions.forEach((webSocketSessionId, session) -> {
            String sessionRoomName = stompToWebSocketMap.get(webSocketSessionId); // 매핑된 방 이름 가져오기
            if (roomName.equals(sessionRoomName)) { // roomName이 일치하는 세션에만 전송
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    log.error("메시지 전송 실패: WebSocket Session ID = {}, message = {}", webSocketSessionId, message, e);
                    throw new CommonException(CommonError.WEB_SOCKET_MESSAGE_SEND_ERROR);
                }
            }
        });
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        try {
            sessions.remove(session.getId());
            stompToWebSocketMap.remove(session.getId()); // 매핑 제거
            System.out.println("afterConnectionClosed: " + session.getId() + "| CloseStatus : " + status);
        } catch (Exception e) {
            throw new CommonException(CommonError.WEB_SOCKET_CLOSE_ERROR);
        }
    }

    // stomp session id, websocket session id 매핑
    public void matchStompSessionToWebSocket(String stompSessionId, String webSocketSessionId) {
        stompToWebSocketMap.put(stompSessionId, webSocketSessionId);
    }

    public String getWebSocketSessionIdByStompSessionId(String stompSessionId) {
        return stompToWebSocketMap.get(stompSessionId);
    }

    // WebSocket Session 가져오기
    public WebSocketSession getSession(String webSocketSessionId) {
        return sessions.get(webSocketSessionId);
    }

    // WebSocket Session에서 roomName 가져오기
    public String getRoomNameBySession(String webSocketSessionId) {
        return stompToWebSocketMap.get(webSocketSessionId);
    }

    // WebSocket Session에 연결된 roomName을 저장
    public void setRoomNameBySession(String webSocketSessionId, String roomName) {
        stompToWebSocketMap.put(webSocketSessionId, roomName);
    }
}
