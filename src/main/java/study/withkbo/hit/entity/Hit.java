package study.withkbo.hit.entity;

import jakarta.persistence.*;
import lombok.*;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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


}

