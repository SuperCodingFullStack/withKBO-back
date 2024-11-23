package study.withkbo.friend.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@Setter
@Table(name = "t_friend")
@NoArgsConstructor
@AllArgsConstructor
public class Friend {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long fromUserId; // 친구 신청을 받은 사람

    @Column(nullable = false)
    private Long toUserId; // 친구 신청을 보낸 사람

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    public void updateFriend(State state) {
        this.state = state;
    }
}
