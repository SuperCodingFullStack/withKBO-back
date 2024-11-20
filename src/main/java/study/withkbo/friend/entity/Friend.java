package study.withkbo.friend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@Table(name = "t_friend")
@NoArgsConstructor
@AllArgsConstructor
public class Friend {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fromUserId;

    private Long toUserId;

    @Enumerated(EnumType.STRING)
    private State state;



    public void updateFriend(State state) {
        this.state = state;
    }
}
