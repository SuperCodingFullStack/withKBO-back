package study.withkbo.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.withkbo.chat.dto.response.UserInviteResponseDto;
import study.withkbo.chat.entity.ChatInvitation;
import study.withkbo.chat.entity.ChatRoom;
import study.withkbo.chat.repository.ChatInvitationRepository;
import study.withkbo.chat.repository.ChatRoomRepository;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.friend.entity.Friend;
import study.withkbo.friend.entity.State;
import study.withkbo.friend.repository.FriendRepository;
import study.withkbo.security.UserDetailsImpl;
import study.withkbo.user.entity.User;
import study.withkbo.user.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatInvitationRepository chatInvitationRepository;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    // 초대 메서드
    @Transactional
    public ChatInvitation inviteInvitee(Long roomIdx, List<Long> inviteeIds,User inviter) {
        // 채팅방을 조회
        ChatRoom chatRoom = chatRoomRepository.findById(roomIdx)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));

        ChatInvitation invitation = chatInvitationRepository.findByRoom(chatRoom)
                .stream()
                .findFirst()
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));

        // 사용자 조회
        List<User> invitees = userRepository.findAllById(inviteeIds);

        // 업데이트
        invitation.setInvitee(invitees);
        invitation.setStatus("초대됨");

        chatInvitationRepository.save(invitation);
        return invitation;
    }

    @Transactional
    public List<UserInviteResponseDto> findUsers(Long roomIdx) {
        // 초대자의 id와 초대된 사람들의 id 가져오기
        List<Object[]> invitedUserIds = chatInvitationRepository.findInvitedAndInviterUserIdsByRoomId(roomIdx);
        // Object[] 에 있던 id들을 추출
        List<Long> invitedIds = invitedUserIds.stream()
                .flatMap(Arrays::stream)
                .map(id -> (Long) id)
                .toList();
        // 초대되지 않은 사람들 가져오기
        List<User> canInviteUsers = userRepository.findAll().stream()
                .filter(user -> !invitedIds.contains(user.getId()))
                .toList();

        return canInviteUsers.stream().map(UserInviteResponseDto::new).collect(Collectors.toList());
    }
}
