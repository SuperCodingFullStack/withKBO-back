package study.withkbo.server.service;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    // 웹소켓 서버 측 연결 처리
    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("handleTextMessage: " + session.getId() + sessions);
        System.out.println("handleTextMessage: " + message.getPayload());

        sessions.forEach((key, value) -> {
            System.out.println("key : "+ key + " value : " + value);
            if(!key.equals(session.getId())){ // 같은 아이디가 아니면 메시지를 전달
                try {
                    value.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("afterConnectionClosed: " + session.getId() + "| CloseStatus : " + status);
    }

    public WebSocketSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }
}
