package study.withkbo.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "t_user")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends DateTime {

    @Id // 프라이머리 키 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
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

    @Column(nullable = false, length = 150)
    private String ProfileImg;

}

