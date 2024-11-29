package study.withkbo.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import study.withkbo.chat.entity.ChatInvitation;
import study.withkbo.chat.entity.ChatRoom;
import study.withkbo.chat.repository.ChatInvitationRepository;
import study.withkbo.chat.repository.ChatRoomRepository;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.friend.entity.Friend;
import study.withkbo.friend.entity.State;
import study.withkbo.friend.repository.FriendRepository;
import study.withkbo.user.entity.User;
import study.withkbo.user.repository.UserRepository;

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
    public ChatInvitation inviteInvitee(Long roomIdx, List<Long> inviteeIds, @AuthenticationPrincipal User inviter) {
        // 채팅방을 조회
        ChatRoom chatRoom = chatRoomRepository.findById(roomIdx)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));
        // 사용자 조회
        List<User> invitees = findFriendsById(inviteeIds, inviter);

        ChatInvitation invitation = new ChatInvitation();
        invitation.setRoom(chatRoom);
        invitation.setInviter(inviter);
        invitation.setInvitee(invitees);
        invitation.setStatus("초대됨");

        chatInvitationRepository.save(invitation);
        return invitation;
    }

    // 임시 친구 조회 메서드
    private List<User> findFriendsById(List<Long> inviteeIds, User inviter) {
        // Success 상태인 친구 조회
        List<Friend> friends = friendRepository.findAllByFromUserIdAndState(inviter.getId(), State.SECCESS);
        friends.addAll(friendRepository.findAllByToUserIdAndState(inviter.getId(), State.SECCESS));

        List<User> invitees = friends.stream()
                .filter(friend -> inviteeIds.contains(friend.getToUserId()) || inviteeIds.contains(friend.getFromUserId()))
                .map(friend -> {
                    if (friend.getFromUserId() == inviter.getId()) {
                        return userRepository.findById(friend.getToUserId()).orElse(null);
                    } else {
                        return userRepository.findById(friend.getFromUserId()).orElse(null);
                    }
                })
                .collect(Collectors.toList());
        return invitees;
    }
}
