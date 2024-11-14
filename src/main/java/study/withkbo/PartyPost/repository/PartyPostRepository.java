package study.withkbo.PartyPost.repository;

import study.withkbo.Comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.PartyPost.entity.PartyPost;

import java.util.List;


public interface PartyPostRepository extends JpaRepository<PartyPost, Long> {

    // 모든 게시글을 조회하는 것
    // 페이지로 나누어서 조회하는 것
    // 조건에 따른 조회 글작성자, 글내용, 작성일 순?
    List<PartyPost> findByTitleContaining(String keyword);

}
