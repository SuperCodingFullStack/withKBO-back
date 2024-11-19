package study.withkbo.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.withkbo.chat.entity.ChatRoom;
import study.withkbo.chat.repository.ChatRoomRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    // 채팅방 생성
    public ChatRoom createChatRoom(String roomName) {
        if (roomName == null || roomName.isEmpty()) {
            throw new RuntimeException("채팅방 이름이 null이거나 없습니다.");
        }

        if (chatRoomRepository.existsByRoomName(roomName)) {
            throw new RuntimeException("동일한 채팅방 이름이 존재합니다.");
        }

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomName(roomName);
        return chatRoomRepository.save(chatRoom);
    }

    // 전체 채팅방 조회
    public List<ChatRoom> getChatRoomByUserId(Long userId) {
        if (userId == null) {
            throw new RuntimeException("존재하지 않는 유저입니다.");
        }
        return chatRoomRepository.findAllByUserId(userId);
    }

    // 특정 채팅방 조회(부분 문자열 가능)
    public List<ChatRoom> getChatRoomByRoomName(String roomName) {
        return chatRoomRepository.findChatRoomByRoomNameContaining(roomName);
    }
}
