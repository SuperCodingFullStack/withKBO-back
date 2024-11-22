package study.withkbo.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.withkbo.chat.dto.request.ChatRoomRequestDto;
import study.withkbo.chat.entity.ChatRoom;
import study.withkbo.chat.service.ChatRoomService;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;

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
    @GetMapping("/user/{userId}")
    public ApiResponseDto<List<ChatRoom>> getChatRoomByUserId(@PathVariable Long userId) {
        List<ChatRoom> chatRooms = chatRoomService.getChatRoomByUserId(userId);
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
}