package study.withkbo.game.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Element;
import study.withkbo.team.entity.Team;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_game")
public class Game {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @JsonBackReference
    private Team team;

    private String matchDate;
    private String matchTime;
    private String awayTeam;
    private String homeTeamScore;
    private String awayTeamScore;
    private String gameSort;
    private String tv;

    public Game crawledToGameEntity(Element gameInfoElement, Team team){
        this.matchDate = gameInfoElement.select("td.td_date").text();
        this.matchTime = gameInfoElement.select("td.td_time").text();
        this.team = team;
        this.awayTeam = gameInfoElement.select("td.td_team div.info_team.team_away").text().split(" ")[0];
        String homeTeamScore = gameInfoElement.select("td.td_team div.info_team.team_home").text().split(" ")[1];
        String awayTeamScore = gameInfoElement.select("td.td_team div.info_team.team_away").text().split(" ")[1];
        if(homeTeamScore != null && !homeTeamScore.isEmpty()){
            this.homeTeamScore = homeTeamScore;
            this.awayTeamScore = awayTeamScore;
        } else{
            this.homeTeamScore = "";
            this.awayTeamScore = "";
        }
        this.gameSort = gameInfoElement.select("td.td_sort").text();
        this.tv = gameInfoElement.select("td.td_tv").text();

        return this;
    }
}
