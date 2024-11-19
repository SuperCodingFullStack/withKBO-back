package study.withkbo.comment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.*;
import study.withkbo.common.BaseTime;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.user.entity.User;

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

    @ManyToOne(fetch = FetchType.LAZY) // 하나의 유저는 여러개의 게시글을 작성할 수 있다.
    // 게시글에는 반드시 어떤 작성자가 작성했는지 표기가 되어야한다.
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 유저 정보? 가져온다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PartyPost partyPost;  // 댓글이 속한 게시글

    @Column(nullable = false, length = 150)
    private String content;  // 댓글 내용

}
