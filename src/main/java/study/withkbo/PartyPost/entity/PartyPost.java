package study.withkbo.PartyPost.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartyPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 고유 ID

    @Column(nullable = false)
    private Integer userIdx;  // 외래키: User 테이블의 ID (작성자)

    @Column(nullable = false)
    private Integer teamId;  // 외래키: Team 테이블의 ID (응원팀)

    @Column(nullable = false)
    private Integer gameInfoId;  // 외래키: Game 테이블의 ID (경기정보가져오기)

    @Column(nullable = false, length = 70)
    private String title;  // 글 제목

    @Column(nullable = false, length = 500)
    private String content;  // 글 내용

    @Column(nullable = false)
    private Integer maxPeopleNum;  // 최대 인원

    @Column(nullable = false)
    private Integer currentPeopleNum;  // 현재 모집된 인원

    @Column(nullable = false)
    private Integer likeCount;  // 좋아요 개수 결국 이것도 해당글을 x명이 좋아합니다 표현해야함

    @Column(nullable = false)
    private Integer hitCount;  // 조회수

    @Column(nullable = false)
    private boolean postState;  // 글 활성화 여부

    @Column(nullable = false)
    private LocalDateTime createdAt;  // 글 작성 시간
}
