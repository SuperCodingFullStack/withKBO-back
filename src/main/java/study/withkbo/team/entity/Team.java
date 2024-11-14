package study.withkbo.team.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.jsoup.nodes.Element;
import study.withkbo.game.entity.Game;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.withkbo.player.entity.Player;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Game> games;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Player> players;

    private int ranking;
    private String teamName;
    private int gamesPlayed;
    private int win;
    private int loss;
    private int draw;
    private Float winRate;
    private String consecutive;
    private String last10Games;

    public Team crawledToEntity(Element baseballTeam) {
        this.ranking = Integer.parseInt(getTextSafe(baseballTeam.selectFirst("th")));
        this.teamName = getTextSafe(baseballTeam.selectFirst("span:nth-child(2)"));
        this.gamesPlayed = Integer.parseInt(getTextSafe(baseballTeam.selectFirst("td:nth-child(3)")));
        this.win = Integer.parseInt(getTextSafe(baseballTeam.selectFirst("td:nth-child(4)")));
        this.loss = Integer.parseInt(getTextSafe(baseballTeam.selectFirst("td:nth-child(5)")));
        this.draw = Integer.parseInt(getTextSafe(baseballTeam.selectFirst("td:nth-child(6)")));
        this.winRate = Float.valueOf(getTextSafe(baseballTeam.selectFirst("td:nth-child(7)")));
        this.consecutive = getTextSafe(baseballTeam.selectFirst("td:nth-child(9)"));
        this.last10Games = getTextSafe(baseballTeam.selectFirst("td:nth-child(12)"));

        return this;
    }

    private String getTextSafe(Element element) {
        return (element != null && !element.text().isEmpty()) ? element.text() : "0";
    }

    public void updateTeam(Team newTeam) {
        this.ranking = newTeam.getRanking();
        this.teamName = newTeam.getTeamName();
        this.gamesPlayed = newTeam.getGamesPlayed();
        this.win = newTeam.getWin();
        this.loss = newTeam.getLoss();
        this.draw = newTeam.getDraw();
        this.winRate = newTeam.getWinRate();
        this.consecutive = newTeam.getConsecutive();
        this.last10Games = newTeam.getLast10Games();
    }
}
