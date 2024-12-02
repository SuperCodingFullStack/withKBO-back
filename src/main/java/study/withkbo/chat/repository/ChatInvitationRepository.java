package study.withkbo.chat.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import study.withkbo.chat.entity.ChatInvitation;
import study.withkbo.chat.entity.ChatRoom;
import study.withkbo.user.entity.User;

import java.util.List;

public interface ChatInvitationRepository extends JpaRepository<ChatInvitation, Long> {
    List<ChatInvitation> findByRoomAndInviter(ChatRoom room, User inviter);
    List<ChatInvitation> findByRoomAndInviteeContaining(ChatRoom room, User invitee);


    void deleteByRoom(ChatRoom room);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT COUNT(c) FROM ChatInvitation c WHERE c.room = :chatRoom")
    long countByRoom(ChatRoom chatRoom);
}
