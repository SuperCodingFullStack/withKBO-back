package study.withkbo.hit.entity;

import jakarta.persistence.*;
import lombok.*;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_hit") // 해당 이름 테이블로 매칭되게
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 어떤 유저가 조회했는가

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_post_id", nullable = false)
    private PartyPost partyPost; // 어떤 게시글을 조회했는가

    public Hit(User user, PartyPost partyPost) {
        this.user = user;
        this.partyPost = partyPost;
    }
}

