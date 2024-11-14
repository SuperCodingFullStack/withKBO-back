package study.withkbo.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.withkbo.user.entity.User;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_chat_participants")
public class ChatParticipants {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
