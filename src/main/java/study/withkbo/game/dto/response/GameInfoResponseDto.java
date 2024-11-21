package study.withkbo.game.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String homeTeamLogoImg;
    private String awayTeam;
    private String awayTeamScore;
    private String awayTeamLogoImg;
    private String stadium;
    private String gameSort;
    private String tv;
}
