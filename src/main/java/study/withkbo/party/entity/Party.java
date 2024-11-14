package study.withkbo.party.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "t_party")
public class Party {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //연관관계 설정 예정입니다.
    private Long partyPostId;

    //연관관계 설정 예정입니다.
    private Long userId;

    //연관관계 설정 예정입니다.
    private Long teamId;

    @Column(nullable = false, columnDefinition = "boolean default false" )
    private Boolean accept;


}
