package study.withkbo.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatInvitation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "room_idx", nullable = false)
    private ChatRoom room;

    // Users에서 idx정보 가져오기
    @ManyToOne
    @JoinColumn(name = "inviter_user_idx", nullable = false)
    private ChatRoom inviter;
    // Users에서 idx정보 가져오기
    @ManyToOne
    @JoinColumn(name = "invitee_user_idx", nullable = false)
    private ChatRoom invitee;

    private String status;
}
