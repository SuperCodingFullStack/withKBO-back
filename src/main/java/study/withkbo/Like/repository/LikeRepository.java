package study.withkbo.Like.repository;

import study.withkbo.Comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LikeRepository extends JpaRepository<Comment, Long> {
    // 좋아요.. 누르면 테이블 생성 그리고 게시글에 좋아요 수를 추가한다?
}
