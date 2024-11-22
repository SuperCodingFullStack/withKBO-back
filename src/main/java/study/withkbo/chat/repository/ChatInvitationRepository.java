package study.withkbo.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.chat.entity.ChatInvitation;

public interface ChatInvitationRepository extends JpaRepository<ChatInvitation, Long> {
}
