package study.withkbo.partypost.entity;

import jakarta.persistence.*;
import lombok.*;
import study.withkbo.comment.entity.Comment;
import study.withkbo.common.BaseTime;
import study.withkbo.game.entity.Game;
import study.withkbo.partypost.dto.request.PartyPostUpdateRequestDto;
import study.withkbo.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder(toBuilder = true) // 기존 필드를 복사하여 빌더를 생성
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

    @Column(nullable = false, name = "current_people_num")
    private Integer currentPeopleNum = 1;  // 기본값을 1로 설정 (현재 모집된 인원)

    @Column(nullable = false, name = "like_count")
    private Integer likeCount = 0;  // 기본값을 0으로 설정  // 좋아요 개수 결국 이것도 해당글을 x명이 좋아합니다 표현해야함

    @Column(nullable = false, name = "hit_count")
    private Integer hitCount = 0;  // 기본값을 0으로 설정

    @Column(nullable = false, name = "post_state", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean postState;  // 글 활성화 여부

    @OneToMany(mappedBy = "partyPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>(); // 해당 게시글에 달린 가변적인 댓글들

    // 필요에 따라 추가: 기존 필드 기반 빌더 생성 메서드
    public PartyPost toBuilderWithUpdates(Game game, PartyPostUpdateRequestDto updateDto) {
        return this.toBuilder()
                .game(game)  // 새로운 경기 정보 설정
                .title(updateDto.getTitle()) // 수정할 제목
                .content(updateDto.getContent()) // 수정할 내용
                .maxPeopleNum(updateDto.getMaxPeopleNum()) // 수정할 최대 모집 인원
                .build();
    }

    @PrePersist
    public void prePersist() {
        if (this.likeCount == null) this.likeCount = 0;
        if (this.hitCount == null) this.hitCount = 0;
    }
    //혜정 코드
    public PartyPost(Long partyPostId) { this.id = partyPostId; }

    // 조회수/ 좋아요 관련 메소드들

    // 좋아요 카운트 감소
    public void decrementLikeCount() {
        this.likeCount = Math.max(0, this.likeCount - 1);  // 좋아요 수가 0 미만으로 내려가지 않도록 방지
    }
    // 좋아요 카운트 증가
    public void incrementLikeCount() {
        this.likeCount += 1;  // 좋아요 수를 증가시킴
    }

    // 조회수 증가
    public void incrementHitCount() {
        this.hitCount ++; // 조회 수를 증가 시킴
    }
}