package study.withkbo.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.withkbo.chat.entity.ChatRoom;
import study.withkbo.chat.repository.ChatRoomRepository;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    // 채팅방 생성
    public ChatRoom createChatRoom(String roomName) {
        if (roomName == null || roomName.isEmpty()) {
            throw new CommonException(CommonError.FILE_NOT_FOUND);
        }

        if (chatRoomRepository.existsByRoomName(roomName)) {
            throw new CommonException(CommonError.CONFLICT);
        }

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomName(roomName);
        return chatRoomRepository.save(chatRoom);
    }

    // 전체 채팅방 조회
    public List<ChatRoom> getChatRoomByUserId(Long userId) {
        if (userId == null) {
            throw new CommonException(CommonError.INVALID_INPUT);
        }
        return chatRoomRepository.findAllByUserId(userId);
    }

    // 특정 채팅방 조회(부분 문자열 가능)
    public List<ChatRoom> getChatRoomByRoomName(String roomName) {
        return chatRoomRepository.findChatRoomByRoomNameContaining(roomName);
    }
}
