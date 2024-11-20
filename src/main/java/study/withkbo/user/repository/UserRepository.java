package study.withkbo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
