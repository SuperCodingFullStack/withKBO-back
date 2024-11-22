package study.withkbo.game.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.withkbo.game.entity.Game;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameResponseDto {
    private Long id;
    private String homeTeamName;
    private String awayTeamName;
    private String matchDate;
    private String matchTime;

    public GameResponseDto(Game game) {
        this.id = game.getId();
        this.homeTeamName = game.getTeam().getTeamName();
        this.awayTeamName = game.getAwayTeam();
        this.matchDate = game.getMatchDate();
        this.matchTime = game.getMatchTime();
    }

}
