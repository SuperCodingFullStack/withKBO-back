package study.withkbo.UserLoginLogout.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.withkbo.chat.entity.ChatParticipants;

import java.sql.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class UserEntity {

    @Id // 프라이머리 키 지정
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, length = 30)
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatParticipants> participants;

}

