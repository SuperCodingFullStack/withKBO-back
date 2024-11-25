package study.withkbo.hit.repository;

import study.withkbo.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.hit.entity.Hit;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.user.entity.User;


public interface HitRepository extends JpaRepository<Hit, Long> {
    // 해당 유저가 해당 게시글에 조회한 이력이 있는가?
    boolean existsByUserAndPartyPost(User user, PartyPost partyPost);

}
