package study.withkbo.team.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.withkbo.team.entity.Team;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamInfoResponseDto {

    private Long teamId;
    private int ranking;
    private String teamName;
    private int gamesPlayed;
    private int win;
    private int loss;
    private int draw;
    private Float winRate;
    private String consecutive;
    private String last10Games;
    private String stadium;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public TeamInfoResponseDto(Team team){
        this.teamId = team.getId();
        this.ranking = team.getRanking();
        this.teamName = team.getTeamName();
        this.gamesPlayed = team.getGamesPlayed();
        this.win = team.getWin();
        this.loss = team.getLoss();
        this.draw = team.getDraw();
        this.winRate = team.getWinRate();
        this.consecutive = team.getConsecutive();
        this.last10Games = team.getLast10Games();
        this.stadium = team.getStadium();
        this.latitude = team.getLatitude();
        this.longitude = team.getLongitude();
    }

}
