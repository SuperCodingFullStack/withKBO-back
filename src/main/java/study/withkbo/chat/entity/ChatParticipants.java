package study.withkbo.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatParticipants {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 다대다 중간 매핑
    @ManyToOne
    @JoinColumn(name = "room_idx", nullable = false)
    private ChatRoom room;

    @ManyToOne
    @JoinColumn(name = "user_idx", nullable = false)
    private ChatRoom user;
}
