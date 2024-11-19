package study.withkbo.comment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.*;
import study.withkbo.common.BaseTime;
import study.withkbo.partypost.entity.PartyPost;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_comment")
public class Comment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 댓글 고유 ID

    @Column(nullable = false)
    private Long userIdx;  // 외래키: User 테이블의 ID (작성자)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PartyPost partyPost;  // 댓글이 속한 게시글

    @Column(nullable = false, length = 150)
    private String content;  // 댓글 내용

}
