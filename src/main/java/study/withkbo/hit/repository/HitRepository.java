package study.withkbo.hit.repository;

import study.withkbo.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.hit.entity.Hit;


public interface HitRepository extends JpaRepository<Hit, Long> {
    // 조회수는 조회수 확인만 하면 되는 것 ?
}
