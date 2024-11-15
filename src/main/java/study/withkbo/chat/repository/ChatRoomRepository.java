package study.withkbo.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.withkbo.chat.entity.ChatRoom;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    boolean existsByRoomName(String roomName);

    @Query("SELECT cr FROM ChatRoom cr JOIN cr.participants cp WHERE cp.user.id = :userId")
    List<ChatRoom> findAllByUserId(Long userId);

    // 문자열 부분 검색
    @Query("SELECT cr FROM ChatRoom cr WHERE cr.roomName LIKE %:roomName%")
    List<ChatRoom> findChatRoomByRoomNameContaining(@Param("roomName") String roomName);
}
