package study.withkbo.game.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.withkbo.game.entity.Game;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameInfoResponseDto {
    private Long gameId;
    private String matchDate;
    private String matchTime;
    private String homeTeam;
    private String homeTeamScore;
    private String awayTeam;
    private String awayTeamScore;
    private String stadium;
    private String gameSort;
    private String tv;

    public GameInfoResponseDto(Game game){
        this.gameId = game.getId();
        this.matchDate = game.getMatchDate();
        this.matchTime = game.getMatchTime();
        this.homeTeam = game.getTeam().getTeamName();
        this.homeTeamScore = game.getHomeTeamScore();
        this.awayTeam = game.getAwayTeam();
        this.awayTeamScore = game.getAwayTeamScore();
        this.stadium = game.getTeam().getStadium();
        this.gameSort = game.getGameSort();
        this.tv = game.getTv();
    }
}
