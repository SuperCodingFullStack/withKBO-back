package study.withkbo.friend.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Friend {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fromUserId;

    private Long toUserId;

    @Enumerated(EnumType.STRING)
    private State state;
}
