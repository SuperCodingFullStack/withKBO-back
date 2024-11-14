package study.withkbo.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import study.withkbo.UserLoginLogout.Entity.UserEntity;

import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Lob // 대용량 데이터 가능해짐
    @Column(nullable = false)
    private String message;
}
