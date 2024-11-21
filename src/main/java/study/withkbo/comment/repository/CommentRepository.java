package study.withkbo.comment.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.comment.entity.Comment;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 댓글은 조회 삭제만 가능하면 되지 않은가?

    // 게시글 ID에 해당하는 댓글을 페이지네이션하여 조회
    Page<Comment> findByPartyPostId(Long postId, Pageable pageable);
}
