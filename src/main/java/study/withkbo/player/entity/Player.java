package study.withkbo.player.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.withkbo.team.entity.Team;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @JsonBackReference
    private Team team;

    private int playerNo;
    private String playerName;
    private String playerEngName;
    private String position;
    private String playerImg;
    private int erg;
    private String whip;
    private String cCategory;
    private String cCount;
    private int inning;
    private int strikeOut;
    private BigDecimal batting;
    private int hit;
    private int homeRun;
    private int rbi;
    private int steal;
}
