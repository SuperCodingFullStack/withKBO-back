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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatroom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 채팅방 생성
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/create")
    public ApiResponseDto<ChatRoom> createChatRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDTO,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User inviter = userDetails.getUser();
        ChatRoom chatRoom = chatRoomService.createChatRoom(chatRoomRequestDTO.getRoomName(), inviter);
        return ApiResponseDto.success(MessageType.CREATE, chatRoom);
    }

    // 채팅방 조회
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/user/chatrooms")
    public ApiResponseDto<List<ChatRoomRequestDto>> getChatRoomByUserId(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<ChatRoomRequestDto> chatRooms = chatRoomService.getChatRoomByUserId(user.getId());
        return ApiResponseDto.success(MessageType.RETRIEVE, chatRooms);
    }
    // 특정 채팅방 조회 (부분 문자열도 가능)
    @GetMapping("/room/{roomName}")
    public ApiResponseDto<List<ChatRoomRequestDto>> getChatRoomByRoomName(@PathVariable String roomName) {
        List<ChatRoom> rooms = chatRoomService.getChatRoomByRoomName(roomName);
        if (rooms.isEmpty()) {
            throw new CommonException(CommonError.NOT_FOUND);
        }
        List<ChatRoomRequestDto> requestDtos = rooms.stream()
                .map(room -> {
                    String formattedDate = room.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "T00:00:00";
                    LocalDateTime localDateTime = LocalDateTime.parse(formattedDate);
                    return ChatRoomRequestDto.builder()
                            .id(room.getId())
                            .roomName(room.getRoomName())
                            .createdDate(localDateTime)
                            .build();
                })
                .collect(Collectors.toList());
        return ApiResponseDto.success(MessageType.RETRIEVE, requestDtos);
    }

    // 채팅방 나가기
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @DeleteMapping("rooms/{roomId}/leave")
    public ApiResponseDto<Boolean> leaveRoom(@PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
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