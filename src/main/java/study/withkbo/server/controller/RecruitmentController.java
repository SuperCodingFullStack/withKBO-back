package study.withkbo.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.withkbo.chat.entity.ChatMessage;
import study.withkbo.chat.service.ChatRoomService;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
import study.withkbo.server.dto.request.RecruitRequestDto;
import study.withkbo.server.dto.response.RecruitResponseDto;
import study.withkbo.user.entity.User;

@RestController
@RequiredArgsConstructor
public class RecruitmentController {
    private final ChatRoomService chatRoomService;

    @MessageMapping("/chat/{roomName}") // 메시지 수신
    @SendTo("/topic/{roomName}")  // 수신된 메시지 브로드 캐스트로 송신
    public ApiResponseDto<RecruitResponseDto> recruitUser(@AuthenticationPrincipal User user, @RequestBody RecruitRequestDto request) {
        ChatMessage chatMessage = request.getChatMessage();
        chatMessage.setUser(user);
        chatRoomService.sendMessageToRoom(chatMessage);

        RecruitResponseDto responseDto = new RecruitResponseDto(chatMessage, user);
        return ApiResponseDto.success(MessageType.CREATE, responseDto);
    }
}
