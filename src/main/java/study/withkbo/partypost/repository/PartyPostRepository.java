package study.withkbo.partypost.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.partypost.entity.PartyPost;

import java.util.Optional;


public interface PartyPostRepository extends JpaRepository<PartyPost, Long> {

    // PartyPost와 연관된 User, Game, Comments 엔티티를 즉시 로딩
    @EntityGraph(attributePaths = {"user", "game", "comments"})
    Optional<PartyPost> findById(@NonNull Long postId);

    // Specification을 사용하여 동적 쿼리와 페이징을 처리
    Page<PartyPost> findAll(Specification<PartyPost> spec, Pageable pageable);

}
