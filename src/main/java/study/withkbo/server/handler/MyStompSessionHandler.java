package study.withkbo.server.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;

import java.io.IOException;
import java.lang.reflect.Type;
// 웹소켓을 통해 메시지 송수신, 구독 등을 처리(채팅방에서)
@Slf4j
public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    private final ChatWebSocketHandler chatWebSocketHandler;

    public MyStompSessionHandler(ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("STOMP session connected: stompSessionID =  {} ", session.getSessionId());
        // WebSocket 세션 ID를 STOMP 세션 ID와 매핑
        WebSocketSession webSocketSession = chatWebSocketHandler.getSession(session.getSessionId());
        if (webSocketSession != null) {
            try {
            chatWebSocketHandler.matchStompSessionToWebSocket(session.getSessionId(), webSocketSession.getId());
            log.info("WebSocket sessionId = {}, STOMP sessionId = {}", webSocketSession.getId(), session.getSessionId());
            webSocketSession.sendMessage(new TextMessage("STOMP 연결됨"));
            } catch (IOException e) {
                log.error("WebSocket 메시지 전송 오류: {}",e.getMessage() ,e);
                throw new CommonException(CommonError.WEB_SOCKET_MESSAGE_ERROR);
            }
        } else {
            log.warn("STOMP session ID에 해당하는 WebSocketSession을 찾을 수 없습니다.");
            throw new CommonException(CommonError.WEB_SOCKET_MAPPING_SESSION_ERROR);
        }

        // roomName을 사용한 메시지 전송 및 구독
        String roomName = chatWebSocketHandler.getRoomNameBySession(session.getSessionId());
        if (roomName != null) {
            session.subscribe("/topic/" + roomName, new StompFrameHandler() {
                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return String.class;
                }
                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                    log.info("수신된 메시지: {}", payload);
                }
            });
            session.send("/app/recruit/" + roomName, "Hello, Server!");
        } else {
            log.error("채팅방 이름을 찾을 수 없습니다.");
            throw new CommonException(CommonError.NOT_FOUND);
        }
    }


    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        log.error("WebSocket Transport Error: " + exception.getMessage(), exception);
        throw new CommonException(CommonError.WEB_SOCKET_TRANSPORT_ERROR);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        log.error("STOMP Connection failed : " + exception.getMessage(), exception);
        throw new CommonException(CommonError.WEB_SOCKET_STOMP_PROTOCOL_ERROR);
    }
}