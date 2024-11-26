package study.withkbo.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.withkbo.server.dto.request.RecruitRequestDto;
import study.withkbo.server.dto.response.RecruitResponseDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
public class RecruitmentController {
    private final SimpMessagingTemplate template;

    // 각 채팅방에 메시지를 보내는 MessageMapping
    @MessageMapping("/chat/{roomName}") // 메시지 수신
    @SendTo("/topic/{roomName}")  // 수신된 메시지 브로드 캐스트로 송신
    public RecruitResponseDto recruitUser(@RequestBody RecruitRequestDto request) {
        template.convertAndSend("/topic/{roomName}" + request.getMessage(), request);

        RecruitResponseDto response = new RecruitResponseDto();
        response.setRoomName(request.getRoomName());
        response.setSender(request.getSender());
        response.setMessage(request.getMessage());
        response.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return response;
    }
}
