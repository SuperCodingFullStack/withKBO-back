package study.withkbo.partypost.entity;

import jakarta.persistence.*;
import lombok.*;
import study.withkbo.comment.entity.Comment;
import study.withkbo.common.BaseTime;
import study.withkbo.game.entity.Game;
import study.withkbo.user.entity.User;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "t_party_post") // 해당 이름 테이블로 매칭되게
public class PartyPost  extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 게시글 고유 ID

    @ManyToOne(fetch = FetchType.LAZY) // 하나의 유저는 여러개의 게시글을 작성할 수 있다.
    // 게시글에는 반드시 어떤 작성자가 작성했는지 표기가 되어야한다.
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 유저 정보? 가져온다.

    @ManyToOne(fetch = FetchType.LAZY)
    // 하나의 경기정보는 여러 게시글에서 사용될 수 있다.
    // 게시글에는 반드시 어떤 경기정보가 있어야만 한다.
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;  // 응원팀의 경기에 대한 정보? 가져온다.


    @Column(name = "title", nullable = false, length = 100)
    private String title;  // 글 제목

    @Column(nullable = false, length = 500)
    private String content;  // 글 내용

    @Column(nullable = false, name = "max_people_num")
    private Integer maxPeopleNum;  // 최대 인원

    @Column(nullable = false, name = "current_people_num", columnDefinition = "INTEGER DEFAULT 1 CHECK(current_people_num >= 1)")
    private Integer currentPeopleNum;  // 현재 모집된 인원

    @Column(nullable = false, name = "like_count", columnDefinition = "INTEGER DEFAULT 0 CHECK(like_count >= 0)")
    private Integer likeCount;  // 좋아요 개수 결국 이것도 해당글을 x명이 좋아합니다 표현해야함

    @Column(nullable = false, name = "hit_count", columnDefinition = "INTEGER DEFAULT 0 CHECK(hit_count >= 0)")
    private Integer hitCount;  // 조회수

    @Column(nullable = false, name = "post_state", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean postState;  // 글 활성화 여부

    @OneToMany(mappedBy = "partyPost", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Comment> comments = List.of();;  // 해당 게시글에 달린 댓글들


}
