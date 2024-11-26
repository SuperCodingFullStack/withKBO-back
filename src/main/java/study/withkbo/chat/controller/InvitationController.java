package study.withkbo.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.withkbo.chat.dto.request.InviteRequestDto;
import study.withkbo.chat.entity.ChatInvitation;
import study.withkbo.chat.service.InviteService;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatroom")
public class InvitationController {

    private final InviteService inviteService;

    // ChatInvitation -> Void 반환값이 없는 성공적인 응답
    @PostMapping("/{roomIdx}/invite")
    public ApiResponseDto<ChatInvitation> invite(@PathVariable Long roomIdx, @RequestBody InviteRequestDto inviteRequestDto) {
        ChatInvitation chatInvitation = inviteService.inviteInvitee(roomIdx, inviteRequestDto);
        return ApiResponseDto.success(MessageType.SEND, chatInvitation);
    }
}
