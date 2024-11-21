package study.withkbo.user.entity;

import jakarta.persistence.*;
import lombok.*;
import study.withkbo.common.BaseTime;
import study.withkbo.team.entity.Team;

@Table(name = "t_user")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id",callSuper = false)
public class User extends BaseTime {

    @Id // 프라이머리 키 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    @Column(nullable = false, length = 100, name="uEmail")
    private String uEmail;

    @Column(nullable = false, length = 30)
    private String uPwd;

    @Column(nullable = false, length = 30)
    private String uName;

    @Column(nullable = true, length = 30)
    private String uNickname;

    @Column(nullable = false, length = 20)
    private String uPhone;

    @Column(nullable = false)
    private Boolean uPhoneAuth;

    @Column(nullable = false, length = 30)
    private String uAddress;

    @Column(nullable = false)
    private String uStatus;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date deletedAt;

    @Column(nullable = false, length = 150)
    private String ProfileImg;


}

