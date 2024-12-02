package study.withkbo.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.chat.entity.ChatParticipants;

public interface ChatParticipantsRepository extends JpaRepository<ChatParticipants, Long> {
}
