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
public class ChatParticipants {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 다대다 중간 매핑
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
