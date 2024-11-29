package study.withkbo.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.chat.entity.ChatInvitation;
import study.withkbo.chat.entity.ChatRoom;
import study.withkbo.user.entity.User;

import java.util.List;

public interface ChatInvitationRepository extends JpaRepository<ChatInvitation, Long> {
    List<ChatInvitation> findByRoomAndInviter(ChatRoom room, User inviter);
    List<ChatInvitation> findByRoomAndInviteeContaining(ChatRoom room, User invitee);

    long countByRoom(ChatRoom chatRoom);

    void deleteByRoom(ChatRoom room);
}
