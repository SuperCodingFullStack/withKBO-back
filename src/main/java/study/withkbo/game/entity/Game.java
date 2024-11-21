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
    private String awayTeamLogoImg;
    private String gameSort;
    private String tv;

    public Game crawledToGameEntity(Element gameInfoElement, Team team){
        this.matchDate = gameInfoElement.select("td.td_date").text();
        this.matchTime = gameInfoElement.select("td.td_time").text();
        this.team = team;
        this.awayTeam = gameInfoElement.select("td.td_team div.info_team.team_away").text().split(" ")[0];
        this.awayTeamLogoImg = awayLogo(gameInfoElement.select("td.td_team div.info_team.team_away").text().split(" ")[0]);
        if(homeTeamScore != null && !homeTeamScore.isEmpty()){
            this.homeTeamScore = gameInfoElement.select("td.td_team div.info_team.team_home").text().split(" ")[1];
            this.awayTeamScore = gameInfoElement.select("td.td_team div.info_team.team_away").text().split(" ")[1];
        } else{
            this.homeTeamScore = "";
            this.awayTeamScore = "";
        }
        this.gameSort = gameInfoElement.select("td.td_sort").text();
        this.tv = gameInfoElement.select("td.td_tv").text();

        return this;
    }

    private String awayLogo(String awayTeamName) {
        return switch (awayTeamName) {
            case "KIA" -> "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/KIA_LOGO.png";
            case "두산" -> "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/DOOSAN_LOGO.png";
            case "삼성" -> "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/SAMSUNG_LOGO.png";
            case "LG" -> "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/LG_LOGO.png";
            case "키움" -> "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/KIWOOM_LOGO.png";
            case "SSG" -> "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/SSG_LOGO.png";
            case "한화" -> "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/HANWHA_LOGO.png";
            case "롯데" -> "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/LOTTE_LOGO.png";
            case "NC" -> "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/NC_LOGO.png";
            case "KT" -> "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/KT_LOGO.png";
            default -> "";
        };
    }
}
