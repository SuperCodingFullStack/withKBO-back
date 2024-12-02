package study.withkbo.config;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import study.withkbo.jwt.JwtUtil;

import java.util.ArrayList;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    private final JwtUtil jwtUtil;
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
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
                // STOMP 연결 요청 확인
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    // Authorization 헤더에서 JWT 추출
                    String token = accessor.getFirstNativeHeader("Authorization");
                    if (token != null) {
                        // JwtUtil을 사용하여 JWT 검증
                        if (!jwtUtil.validateToken(token)) {
							Claims claims = jwtUtil.getUserInfoFromToken(token);
							String userId = claims.getSubject(); // 토큰에서 사용자 정보 (userId)를 추출-
							Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
							SecurityContextHolder.getContext().setAuthentication(authentication);
                        } else {
							log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
						}
                    } else {
                        log.error("Ill-Formed JWT token OR Missing Authentication, 형식이 잘못된 JWT token 또는 Authentication이 누락되었습니다.");
                    }
                }
                return message;
            }
        });
    }

}
