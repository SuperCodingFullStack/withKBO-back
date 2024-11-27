package study.withkbo.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.withkbo.chat.dto.request.InviteRequestDto;
import study.withkbo.chat.entity.ChatInvitation;
import study.withkbo.chat.entity.ChatParticipants;
import study.withkbo.chat.entity.ChatRoom;
import study.withkbo.chat.repository.ChatInvitationRepository;
import study.withkbo.chat.repository.ChatRoomRepository;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatInvitationRepository chatInvitationRepository;

    public ChatInvitation inviteInvitee(Long roomIdx, InviteRequestDto inviteRequestDto) {
        // 채팅방을 조회
        ChatRoom chatRoom = chatRoomRepository.findById(roomIdx)
                .orElseThrow(() -> new RuntimeException("채팅방이 존재하지 않습니다."));
        // 초대받는 사람 정보 가져오기
        Long inviteeUserId = inviteRequestDto.getInviteeUserId();
        // 초대받는 사람 추가
        ChatParticipants chatParticipants = new ChatParticipants();
        chatParticipants.setRoom(chatRoom);

        Long inviterUserId = inviteRequestDto.getInviterUserId();
        // 초대장 생성
        ChatInvitation chatInvitation = new ChatInvitation();
        // 초대방 설정
        chatInvitation.setRoom(chatRoom);
        // 초대자 정보 설정
//        chatInvitation.setInviter(inviter);
        // 초대받을 사람 정보 설정
//        chatInvitation.setInvitee(invitee);
        // Status
        chatInvitation.setStatus("대기");
        // db저장
        chatInvitationRepository.save(chatInvitation);
        return chatInvitation;
    }
}
