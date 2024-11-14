package study.withkbo.chat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "room_idx", nullable = false)
    private ChatRoom room;

    @ManyToOne
    @JoinColumn(name = "user_idx", nullable = false)
    private ChatRoom user;

    @Lob // 대용량 데이터 가능해짐
    @Column(nullable = false)
    private String message;

    @Temporal(TemporalType.TIMESTAMP) // 날짜/시간 값을 매핑
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP") // 필드의 기본값을 현재 시간으로 설정
    private Date createdAt;
}
