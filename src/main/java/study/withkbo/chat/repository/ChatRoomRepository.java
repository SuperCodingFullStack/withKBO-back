package study.withkbo.chat.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.withkbo.chat.entity.ChatRoom;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    boolean existsByRoomName(String roomName);

    @Query("SELECT cr FROM ChatRoom cr JOIN ChatParticipants cp ON cr.id = cp.room.id WHERE cp.user.id = :userId")
    List<ChatRoom> findChatRoomByUserId(Long userId);

    // 문자열 부분 검색
    List<ChatRoom> findChatRoomByRoomNameContaining(String roomName);

    // 비관적 락, 읽기만 허용
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT c FROM ChatRoom c WHERE c.id = :roomId")
    ChatRoom findByIdForUpdate(Long roomId);
}
