package study.withkbo.friend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.friend.entity.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
