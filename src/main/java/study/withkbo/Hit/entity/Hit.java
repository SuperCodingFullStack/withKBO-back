package study.withkbo.Hit.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 댓글 고유 ID

    @Column(nullable = false)
    private Long userIdx;  // 외래키: User 테이블의 ID (작성자)

    @Column(nullable = false)
    private Long partyPostId;  // 외래키: PartyPost 테이블의 ID (어떤 게시글을 조회한 건가)

}
