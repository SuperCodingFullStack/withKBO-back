package study.withkbo.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.withkbo.chat.dto.request.ChatRoomRequestDto;
import study.withkbo.chat.entity.ChatRoom;
import study.withkbo.chat.service.ChatRoomService;
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
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 채팅방 생성
    @PostMapping("/create")
    public ApiResponseDto<ChatRoom> createChatRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDTO) {
        ChatRoom chatRoom = chatRoomService.createChatRoom(chatRoomRequestDTO.getRoomName());
        return ApiResponseDto.success(MessageType.CREATE, chatRoom);
    }

    // 채팅방 조회
    @GetMapping("/user/chatrooms")
    public ApiResponseDto<List<ChatRoom>> getChatRoomByUserId(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<ChatRoom> chatRooms = chatRoomService.getChatRoomByUserId(user.getId());
        return ApiResponseDto.success(MessageType.RETRIEVE, chatRooms);
    }
    // 특정 채팅방 조회 (부분 문자열도 가능)
    @GetMapping("/room/{roomName}")
    public ApiResponseDto<List<ChatRoom>> getChatRoomByRoomName(@PathVariable String roomName) {
        List<ChatRoom> rooms = chatRoomService.getChatRoomByRoomName(roomName);
        if (rooms.isEmpty()) {
            throw new CommonException(CommonError.NOT_FOUND);
        }
        return ApiResponseDto.success(MessageType.RETRIEVE, rooms);
    }

    // 채팅방 나가기
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @DeleteMapping("rooms/{roomId}/leave")
    public ApiResponseDto<Boolean> leaveRoom(@PathVariable Long roomId, @AuthenticationPrincipal User user) {
        chatRoomService.leaveChatRoom(roomId, user);
        return ApiResponseDto.success(MessageType.DELETE, true);
    }

    // 채팅방 삭제
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @DeleteMapping("rooms/{roomId}")
    public ApiResponseDto<Boolean> deleteRoom(@PathVariable Long roomId) {
        chatRoomService.ridOffChatRoom(roomId);
        return ApiResponseDto.success(MessageType.DELETE, true);
    }
}