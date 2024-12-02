package study.withkbo.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.withkbo.chat.dto.request.InviteRequestDto;
import study.withkbo.chat.entity.ChatInvitation;
import study.withkbo.chat.service.InviteService;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.user.entity.User;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatroom")
public class InvitationController {

    private final InviteService inviteService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER,ROLE_ADMIN')")
    @PostMapping("/invite/{roomIdx}")
    public ApiResponseDto<ChatInvitation> invite(@PathVariable Long roomIdx
            , @RequestBody InviteRequestDto inviteRequestDto,
            @AuthenticationPrincipal User inviter) {
        ChatInvitation chatInvitation = inviteService.inviteInvitee(roomIdx, inviteRequestDto.getInviteeUserId(),inviter);
        return ApiResponseDto.success(MessageType.SEND, chatInvitation);
    }
}
