package study.withkbo.team.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamInfoResponse {

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
}
