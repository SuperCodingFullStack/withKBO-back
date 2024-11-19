package study.withkbo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.withkbo.user.entity.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

}
