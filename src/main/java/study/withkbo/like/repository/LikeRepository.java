package study.withkbo.like.repository;

import study.withkbo.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.like.entity.Like;


public interface LikeRepository extends JpaRepository<Like, Long> {
    // 좋아요.. 누르면 테이블 생성 그리고 게시글에 좋아요 수를 추가한다?
}
