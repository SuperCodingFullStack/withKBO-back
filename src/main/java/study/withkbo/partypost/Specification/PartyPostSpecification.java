package study.withkbo.partypost.Specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.game.entity.Game;

public class PartyPostSpecification {

    // 특정 팀 이름 기준으로 필터링
    public static Specification<PartyPost> hasTeam(String teamName) {
        return (root, query, criteriaBuilder) ->
                StringUtils.hasText(teamName) ?
                        criteriaBuilder.equal(root.get("game").get("team").get("teamName"), teamName) : null;
    }

    // 특정 경기 기준으로 필터링
    public static Specification<PartyPost> hasGame(Long gameId) {
        return (root, query, criteriaBuilder) ->
                gameId != null ? criteriaBuilder.equal(root.get("game").get("id"), gameId) : null;
    }

    // 최신순 정렬
    public static Specification<PartyPost> orderByCreatedAtDesc() {
        return (root, query, criteriaBuilder) -> {
            if (root.get("createdDate") != null) {
            query.orderBy(criteriaBuilder.desc(root.get("createdDate")));
            }
            return null; // 정렬만 적용하므로 null 반환
        };
    }
    // 작성순 정렬
    public static Specification<PartyPost> orderByCreatedAtAsc() {
        return (root, query, criteriaBuilder) -> {
            if (root.get("createdDate") != null) {
                query.orderBy(criteriaBuilder.asc(root.get("createdDate")));
            }
            return null; // 정렬만 적용하므로 null 반환
        };
    }

    // 조회수 기준 정렬 (hitCount 기준으로 변경)
    public static Specification<PartyPost> orderByHitCountDesc() {
        return (root, query, criteriaBuilder) -> {
            if (root.get("hitCount") != null) {
                query.orderBy(criteriaBuilder.desc(root.get("hitCount")));
            }
            return null;
        };
    }

    // 좋아요 기준 정렬 (likeCount 기준으로 변경)
    public static Specification<PartyPost> orderByLikeCountDesc() {
        return (root, query, criteriaBuilder) -> {
            if (root.get("likeCount") != null) {
                query.orderBy(criteriaBuilder.desc(root.get("likeCount")));
            }
            return null;
        };
    }


    public static Specification<PartyPost> createdAfterCursor(Long cursor) {
        return (root, query, criteriaBuilder) ->
                cursor == null ? criteriaBuilder.conjunction()
                        : criteriaBuilder.greaterThan(root.get("id"), cursor);

    }

    public static Specification<PartyPost> createdBeforeCursor(Long cursor) {
        return (root, query, criteriaBuilder) ->
                cursor == null ? criteriaBuilder.conjunction()
                        : criteriaBuilder.lessThan(root.get("id"), cursor);
    }
}

