package study.withkbo.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "room_name", nullable = false, unique = true)
    private String roomName;

    @Temporal(TemporalType.TIMESTAMP) // 날짜,시간 값을 TIMESTAMP 형식으로 저장
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP") // 기본값을 현재 시간으로 설정
    private Date createdAt;

    // Users에서도 추가해줄것,
    // cascade는 전파 수행이고 Chatroom의 변경사항이 ChatParticipants에 자동으로 변경
    // orphanRemoval는 Chatroom과의 관계가 끊어진 ChatParticipants가 자동으로 삭제
    @OneToMany(mappedBy = "chat_room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatParticipants> participants;
}
