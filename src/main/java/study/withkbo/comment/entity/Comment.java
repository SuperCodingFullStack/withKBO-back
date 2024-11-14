package study.withkbo.comment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 댓글 고유 ID

    @Column(nullable = false)
    private Long userIdx;  // 외래키: User 테이블의 ID (작성자)

    @Column(nullable = false)
    private Long partyPostId;  // 외래키: PartyPost 테이블의 ID (어떤 게시글에 달린 댓글인지)

    @Column(nullable = false, length = 150)
    private String content;  // 댓글 내용

    @Column(nullable = false, updatable = false) // 비어있을 수 없으며, 추가 후 업데이트 불가능
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 이렇게 수정하면 데이터베이스에 저장이 잘되는가
    private LocalDateTime createdAt;  // 댓글 작성 시간

    // 댓글 생성 시 시간 자동 저장
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    // 해당 기능이 인서트 쿼리실행전 실행된다.
}
