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

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column( length = 30)
    private String nickname;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String address;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role = UserRoleEnum.USER;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

    @Column(length = 150)
    private String profileImg;

    private Long kakaoId;

    public User(String nickname, String encodedPassword, String email, UserRoleEnum userRoleEnum, Long kakaoId) {
        this.nickname = nickname;
        this.password = encodedPassword;
        this.role = userRoleEnum;
        this.kakaoId = kakaoId;
        this.username = email;
        this.isDeleted = false;
    }

    public void updatePassword(String encode) {
        this.password = encode;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}

