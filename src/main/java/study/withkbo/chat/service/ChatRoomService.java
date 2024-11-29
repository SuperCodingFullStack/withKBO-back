package study.withkbo.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import study.withkbo.chat.entity.ChatInvitation;
import study.withkbo.chat.entity.ChatMessage;
import study.withkbo.chat.entity.ChatRoom;
import study.withkbo.chat.repository.ChatInvitationRepository;
import study.withkbo.chat.repository.ChatMessageRepository;
import study.withkbo.chat.repository.ChatRoomRepository;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.user.entity.User;
import study.withkbo.user.repository.UserRepository;
import study.withkbo.user.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final SimpMessagingTemplate template;
    private final ChatInvitationRepository chatInvitationRepository;
    private final ChatMessageRepository chatMessageRepository;

    // 채팅방 생성
    public ChatRoom createChatRoom(String roomName) {
        if (roomName == null || roomName.isEmpty()) {
            throw new CommonException(CommonError.NOT_FOUND);
        }
        // 채팅방이 이미 존재하는 경우
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

    // 채팅방 나가기
    public void leaveChatRoom(Long roomId, @AuthenticationPrincipal User user) {
        getOutChatRoom(roomId, user);
    }

    // 채팅방에서 유저 삭제
    public void getOutChatRoom(Long roomId, @AuthenticationPrincipal User user) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));
        List<ChatInvitation> invitationsFromUser= chatInvitationRepository.findByRoomAndInviter(chatRoom, user);
        List<ChatInvitation> invitationsToUser= chatInvitationRepository.findByRoomAndInviteeContaining(chatRoom, user);
        // List 합침
        invitationsFromUser.addAll(invitationsToUser);
        if (invitationsFromUser.isEmpty()) {
            throw new CommonException(CommonError.NOT_FOUND);
        }
        chatInvitationRepository.deleteByRoom(chatRoom);
        deleteChatRoom(chatRoom);
    }

    // 채팅방이 0명이 될 경우 채팅방 삭제
    private void deleteChatRoom(ChatRoom chatRoom) {
        long userCount = chatInvitationRepository.countByRoom(chatRoom);
        if (userCount == 0) {
            chatRoomRepository.delete(chatRoom);
        }
    }

    // 채팅방 삭제
    public void ridOffChatRoom(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));
        chatInvitationRepository.deleteByRoom(chatRoom);
        chatMessageRepository.deleteByRoom(chatRoom);
        chatRoomRepository.delete(chatRoom);
    }

    // 메시지 전송 로직
    public void sendMessageToRoom(ChatMessage chatMessage) {
        if (chatMessage == null || chatMessage.getRoom() == null || chatMessage.getRoom().getRoomName() == null || chatMessage.getRoom().getRoomName().isEmpty()) {
            throw new CommonException(CommonError.INVALID_INPUT);
        }
        String roomName = chatMessage.getRoom().getRoomName();
        template.convertAndSend("/topic/" + roomName, chatMessage); // 해당 채팅방에 메시지 브로드캐스트
    }

}
