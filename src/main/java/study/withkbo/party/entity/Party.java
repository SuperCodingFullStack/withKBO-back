package study.withkbo.party.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.user.entity.User;

@Entity
@Getter
@Builder
@Table(name = "t_party")
@NoArgsConstructor
@AllArgsConstructor
public class Party {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "party_post_id")
    private PartyPost partyPost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Column(nullable = false, columnDefinition = "boolean default false" )
    private Boolean accept;


    public Party(Long partyPostId, User user) {
        this.user = user;
        this.partyPost = new PartyPost(partyPostId);
    }
}
