package study.withkbo.like.entity;

import jakarta.persistence.*;
import lombok.*;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_like") // 해당 이름 테이블로 매칭되게
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

    public Like(User user, PartyPost partyPost) {
        this.user = user;
        this.partyPost = partyPost;
    }

}
