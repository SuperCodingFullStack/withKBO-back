package study.withkbo.user.entity;

import jakarta.persistence.*;
import lombok.*;
import study.withkbo.common.BaseTime;
import study.withkbo.friend.entity.State;
import study.withkbo.team.entity.Team;

@Table(name = "t_user")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id",callSuper = false)
@ToString
public class User extends BaseTime {

    @Id // 프라이머리 키 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = true, length = 30)
    private String nickname;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 100)
    private String address;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role = UserRoleEnum.USER;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

    @Column(nullable = false, length = 150)
    private String profileImg;


}

