package study.withkbo.Like.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 댓글 고유 ID

    @Column(nullable = false)
    private Long userIdx;  // 외래키: User 테이블의 ID (작성자)

    @Column(nullable = false)
    private Long partyPostId;  // 외래키: PartyPost 테이블의 ID (어떤 게시글의 좋아요인가)
}
