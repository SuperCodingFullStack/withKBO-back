package study.withkbo.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.notification.entity.Notification;

public interface NotifyRepository extends JpaRepository<Notification, Long> {
}
