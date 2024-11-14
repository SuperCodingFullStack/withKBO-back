package study.withkbo.Like.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 댓글 고유 ID

    @Column(nullable = false)
    private Integer userIdx;  // 외래키: User 테이블의 ID (작성자)

    @Column(nullable = false)
    private Integer partyPostId;  // 외래키: PartyPost 테이블의 ID (어떤 게시글의 좋아요인가)
}
