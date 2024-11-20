package study.withkbo.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class User extends BaseTime {

    @Id // 프라이머리 키 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    @Column(nullable = false, length = 100, name="uEmail")
    private String uEmail;

    @Column(nullable = false, length = 30, name="u_pwd",columnDefinition = "LONGTEXT")
    private String uPwd;

    @Column(nullable = false, length = 30, name="u_name")
    private String uName;

    @Column(nullable = true, length = 30, name="u_nickname")
    private String uNickname;

    @Column(nullable = false, length = 20, name="u_phone")
    private String uPhone;

    @Column(nullable = false,name="u_phoneAuth")
    private Boolean uPhoneAuth;

    @Column(nullable = false, length = 100, name="u_address")
    private String uAddress;

    @Column(nullable = false, name="u_status")
    private String uStatus;

    @Column(nullable = false, length = 150, name="profile_img")
    private String profileImg;

}

