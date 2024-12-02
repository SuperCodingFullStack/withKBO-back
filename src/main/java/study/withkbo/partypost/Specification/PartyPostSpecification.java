package study.withkbo.partypost.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import study.withkbo.like.entity.Like;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.user.entity.User;

public class PartyPostSpecification {

    // 특정 팀 이름 기준으로 필터링
    public static Specification<PartyPost> hasTeam(String teamName) {
        return (root, query, criteriaBuilder) ->
                StringUtils.hasText(teamName) ?
                        criteriaBuilder.equal(root.get("game").get("team").get("teamName"), teamName)
                        : criteriaBuilder.conjunction(); // 조건이 없을 때 항상 true
    }

    // 특정 경기 기준으로 필터링
    public static Specification<PartyPost> hasGame(Long gameId) {
        return (root, query, criteriaBuilder) ->
                gameId != null ?
                        criteriaBuilder.equal(root.get("game").get("id"), gameId)
                        : criteriaBuilder.conjunction(); // 조건이 없을 때 항상 true
    }

    // 최신순 정렬
    public static Specification<PartyPost> orderByCreatedAtDesc() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("createdDate")));
            return criteriaBuilder.conjunction(); // 항상 true 반환
        };
    }

    // 작성순 정렬
    public static Specification<PartyPost> orderByCreatedAtAsc() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(root.get("createdDate")));
            return criteriaBuilder.conjunction(); // 항상 true 반환
        };
    }

    // 조회수 기준 정렬 (hitCount 기준으로 변경)
    public static Specification<PartyPost> orderByHitCountDesc() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("hitCount")));
            return criteriaBuilder.conjunction(); // 항상 true 반환
        };
    }

    // 좋아요 기준 정렬 (likeCount 기준으로 변경)
    public static Specification<PartyPost> orderByLikeCountDesc() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("likeCount")));
            return criteriaBuilder.conjunction(); // 항상 true 반환
        };
    }

    // 특정 사용자가 좋아요를 누른 게시글만 필터링하는 Specification
    public static Specification<PartyPost> hasLikedByUser(User user) {
        return (root, query, criteriaBuilder) -> {
            // "likes" 필드가 Join을 통해 연결되어야 할 수도 있습니다.
            Join<PartyPost, Like> likeJoin = root.join("likes", JoinType.LEFT);
            return criteriaBuilder.equal(likeJoin.get("user"), user);
        };
    }

    // 특정 사용자가 작성한 게시글만 필터링하는 Specification
    public static Specification<PartyPost> hasPostsByUser(User user) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("user"), user);
        };
    }
}
