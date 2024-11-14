package study.withkbo.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.withkbo.UserLoginLogout.Entity.UserEntity;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatInvitation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    // Users에서 idx정보 가져오기
    @ManyToOne
    @JoinColumn(name = "inviter_user_id", nullable = false)
    private UserEntity inviter;
    // Users에서 idx정보 가져오기
    @ManyToOne
    @JoinColumn(name = "invitee_user_id", nullable = false)
    private UserEntity invitee;

    private String status;
}
