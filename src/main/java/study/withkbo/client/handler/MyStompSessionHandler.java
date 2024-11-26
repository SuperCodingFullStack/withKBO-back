package study.withkbo.client.handler;

import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import study.withkbo.server.service.ChatWebSocketHandler;

import java.io.IOException;
import java.lang.reflect.Type;
// 웹소켓을 통해 메시지 송수신, 구독 등을 처리(채팅방용)
public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    private final ChatWebSocketHandler chatWebSocketHandler;

    public MyStompSessionHandler(ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("STOMP session connected: " + session.getSessionId());
        WebSocketSession webSocketSession = chatWebSocketHandler.getSession(session.getSessionId());
        if (webSocketSession != null) {
            System.out.println("WebSocketSession URI: " + webSocketSession.getUri());
            try {
                // 연결 완료 후 메시지를 전송
                webSocketSession.sendMessage(new TextMessage("STOMP 연결 후 WebSocket 세션 처리"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 메시지 전송 및 구독 처리
        session.send("/app/recruit/{roomName}", "Hello, Server!");
        session.subscribe("/topic/{roomName}", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class; // 수신할 메시지 타입
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("받은 메시지 : " + payload);
            }
        });
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        System.err.println("Transport Error: " + exception.getMessage());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.err.println("STOMP connection failed: " + exception.getMessage());
    }
}