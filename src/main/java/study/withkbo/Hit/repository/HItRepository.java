package study.withkbo.Hit.repository;

import study.withkbo.Comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;



public interface HItRepository extends JpaRepository<Comment, Long> {
    // 조회수는 조회수 확인만 하면 되는 것 ?
}
