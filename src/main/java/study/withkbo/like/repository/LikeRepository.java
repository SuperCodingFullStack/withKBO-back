package study.withkbo.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.like.entity.Like;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.user.entity.User;

import java.util.Optional;


public interface LikeRepository extends JpaRepository<Like, Long> {

    // 특정 유저와 게시글에 대한 좋아요가 있는지 확인
     boolean existsByUserAndPartyPost(User user, PartyPost partyPost);

    // 특정 유저와 게시글에 대한 좋아요를 찾기
    Optional<Like> findByUserAndPartyPost(User user, PartyPost partyPost);
}
