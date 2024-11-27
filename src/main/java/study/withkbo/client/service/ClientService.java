package study.withkbo.client.service;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import study.withkbo.client.handler.MyStompSessionHandler;
import study.withkbo.server.service.ChatWebSocketHandler;

@Service
public class ClientService {
    // 클라이언트가 웹소켓 서버에 연결
    private WebSocketStompClient stompClient;
    @Value("${websocket.server.url}")
    private String serverUrl;
    private final ChatWebSocketHandler chatWebSocketHandler;

    public ClientService(ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;
    }

    @PostConstruct
    public void init() {
        System.out.println("serverUrl: " + serverUrl);
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new StringMessageConverter());

        connect();
    }
    public void connect() {
        System.out.println("Attempting to connect to WebSocket server at: " + serverUrl);
        StompSessionHandler sessionHandler = new MyStompSessionHandler(chatWebSocketHandler);
        stompClient.connect(serverUrl, sessionHandler);
    }
}