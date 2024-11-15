package study.withkbo.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.withkbo.chat.dto.request.ChatRoomRequestDto;
import study.withkbo.chat.entity.ChatRoom;
import study.withkbo.chat.service.ChatRoomService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatroom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 채팅방 생성
    @PostMapping("/create")
    public ResponseEntity<ChatRoom> createChatRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDTO) {
        ChatRoom request = chatRoomService.createChatRoom(chatRoomRequestDTO.getRoomName());
        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }

    // 채팅방 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChatRoom>> getChatRoomByUserId(@PathVariable Long userId) {
        List<ChatRoom> chatRooms = chatRoomService.getChatRoomByUserId(userId);
        return ResponseEntity.ok(chatRooms);
    }
    // 특정 채팅방 조회 (부분 문자열도 가능)
    @GetMapping("/room/{roomName}")
    public ResponseEntity<List<ChatRoom>> getChatRoomByRoomName(@PathVariable String roomName) {
        List<ChatRoom> rooms = chatRoomService.getChatRoomByRoomName(roomName);
        if (rooms.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rooms);
    }
}