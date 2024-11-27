package study.withkbo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/portfolio") // 클라이언트 요청 엔드포인트
				.withSockJS();
	}

	// 메시지를 관리하고 배포하는 역할의 컴포넌트
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.setApplicationDestinationPrefixes("/app");
		config.enableSimpleBroker("/topic", "/queue"); // queue는 1:1, topic은 1대 다수
	}

	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer() {
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(8192); // 8KB
		container.setMaxBinaryMessageBufferSize(8192);
		return container;
	}

	// STOMP 기반 WebSocket 메시징
	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		registry.setTimeToFirstMessage(30000); // 첫 메시지를 기다리는 시간 30초
		registry.setSendTimeLimit(15 * 1000).setSendBufferSizeLimit(512 * 1024);
		registry.setMessageSizeLimit(128 * 1024); // 메시지 크기 128KB
	}

	// 토큰 인증
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		// ChannelInterceptor는 WebSocket 메시지를 처리하기 전에 메시지를 가로채서 사용자의 권한을 확인할 수 있게 도와줍니다.
		registration.interceptors(new ChannelInterceptor() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) { // WebSocket Message, 전송할 채널
				// message에서 StompHeaderAccessor을 얻어 STOMP 메시지의 헤더에 접근
				StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
				if (StompCommand.CONNECT.equals(accessor.getCommand())) { // WebSocket 세션에 연결
					// Access authentication header(s) and invoke accessor.setUser(user) - 인증 헤더 처리, 사용자 정보 설정
				}
				return message;
			}
		});
	}
}
