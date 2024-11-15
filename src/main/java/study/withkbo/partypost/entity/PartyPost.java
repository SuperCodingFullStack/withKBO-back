//package study.withkbo.partypost.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import study.withkbo.common.BaseTime;
//import study.withkbo.game.entity.Game;
//import study.withkbo.user.entity.User;
//
//@Entity
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@EqualsAndHashCode(of = "id", callSuper = false)
//@Table(name = "t_party_post")
//public class PartyPost  extends BaseTime {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;  // 고유 ID
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;  // 유저 정보? 가져온다.
//
//    @ManyToOne
//    @JoinColumn(name = "game_id")
//    private Game game;  // 응원팀의 경기에 대한 정보? 가져온다.
//    // 한게시글은 하나의 경기정보에 대해서 매치된다?
//    // 경기정보는 여러 게시글에서 찾을 수 있다.
//    // game 엔티티에
//    //@OneToMany
//    //@JoinColumn(name = "game_id")
//    //private List<PartyPostEntity> PartyPosts ;
//
////    @Column(nullable = false)
////    private Long gameInfoId;  // 외래키: Game 테이블의 ID (경기정보가져오기)
//
//    @Column(nullable = false, length = 100)
//    private String title;  // 글 제목
//
//    @Column(nullable = false, length = 500)
//    private String content;  // 글 내용
//
//    @Column(nullable = false, name = "max_people_num")
//    private Integer maxPeopleNum;  // 최대 인원
//
//    @Column(nullable = false, name = "current_people_num")
//    private Integer currentPeopleNum;  // 현재 모집된 인원
//
//    @Column(nullable = false, name = "like_count")
//    private Integer likeCount;  // 좋아요 개수 결국 이것도 해당글을 x명이 좋아합니다 표현해야함
//
//    @Column(nullable = false, name = "hit_count")
//    private Integer hitCount;  // 조회수
//
//    @Column(nullable = false, name = "post_state")
//    private boolean postState;  // 글 활성화 여부
//
//}
