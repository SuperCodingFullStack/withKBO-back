package study.withkbo.Comment.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.Comment.entity.Comment;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 댓글은 조회 삭제만 가능하면 되지 않은가?
}
