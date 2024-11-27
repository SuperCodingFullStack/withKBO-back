package study.withkbo.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.withkbo.chat.entity.ChatMessage;
import study.withkbo.server.dto.request.RecruitRequestDto;
import study.withkbo.server.dto.response.RecruitResponseDto;
import study.withkbo.user.entity.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
public class RecruitmentController {
    private final SimpMessagingTemplate template;

    @MessageMapping("/chat/{roomName}") // 메시지 수신
    @SendTo("/topic/{roomName}")  // 수신된 메시지 브로드 캐스트로 송신
    public RecruitResponseDto recruitUser(@RequestBody RecruitRequestDto request) {
        ChatMessage chatMessage = request.getChatMessage();
        User user = chatMessage.getUser();
        template.convertAndSend("/topic/" + chatMessage.getRoom().getRoomName(), chatMessage);
        return new RecruitResponseDto(chatMessage, user);
    }
}
