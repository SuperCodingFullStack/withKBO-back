package study.withkbo.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.notification.entity.Notification;

import java.util.List;
import java.util.Optional;

public interface NotifyRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverIdAndReadStatus(Long id, Notification.ReadStatus readStatus);

    Optional<Notification> findByIdAndReceiverId(Long notificationId, Long id);
}
