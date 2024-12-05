package study.withkbo.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.withkbo.chat.dto.request.InviteRequestDto;
import study.withkbo.chat.dto.response.InviteResponseDto;
import study.withkbo.chat.dto.response.UserInviteResponseDto;
import study.withkbo.chat.entity.ChatInvitation;
import study.withkbo.chat.service.InviteService;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.security.UserDetailsImpl;
import study.withkbo.user.entity.User;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatroom")
public class InvitationController {

    private final InviteService inviteService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/invite/{roomIdx}")
    public ApiResponseDto<InviteResponseDto> inviteToChatRoom(@PathVariable Long roomIdx
            , @RequestBody InviteRequestDto inviteRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User inviter = userDetails.getUser();
        List<Long> inviteeIds = inviteRequestDto.getInviteeUserId();
        ChatInvitation chatInvitation = inviteService.inviteInvitee(roomIdx, inviteeIds, inviter);

        InviteResponseDto inviteResponseDto = new InviteResponseDto(chatInvitation);
        return ApiResponseDto.success(MessageType.SEND, inviteResponseDto);
    }

    @GetMapping("/users/{roomIdx}")
    public ApiResponseDto<List<UserInviteResponseDto>> getUsersForInvite(@PathVariable Long roomIdx){
        List<UserInviteResponseDto> userInviteResponseDto = inviteService.findUsers(roomIdx);
        return ApiResponseDto.success(MessageType.RETRIEVE, userInviteResponseDto);
    }
}
