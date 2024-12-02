package study.withkbo.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.chat.entity.ChatMessage;
import study.withkbo.chat.entity.ChatRoom;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    void deleteByRoom(ChatRoom room);
}
