package study.withkbo.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.withkbo.user.entity.User;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_chat_invitation")
public class ChatInvitation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    @ManyToOne
    @JoinColumn(name = "inviter_user_id", nullable = false)
    private User inviter;

    @ManyToMany
    @JoinTable(name = "t_chat_invitation_invitee"
            , joinColumns = @JoinColumn(name = "chat_invitation_id")
            , inverseJoinColumns = @JoinColumn(name = "invitee_user_id"))
    private List<User> invitee;

    private String status;
}
