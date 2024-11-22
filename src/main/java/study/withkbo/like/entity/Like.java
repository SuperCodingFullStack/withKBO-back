package study.withkbo.like.entity;

import jakarta.persistence.*;
import lombok.*;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_post_id", nullable = false)
    private PartyPost partyPost;


}
