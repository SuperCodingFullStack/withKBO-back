package study.withkbo.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.withkbo.team.dto.TeamInfoResponse;
import study.withkbo.team.entity.Team;
import study.withkbo.team.repository.TeamRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    @Scheduled(cron = "0 0 2 * * *")
    public List<TeamInfoResponse> selectTeamInfo() {
        Elements baseballTeams = crawlingTeamInfo();
        List<String> teamNames = extractTeamName(baseballTeams);
        List<Team> existTeams = findByTeamName(teamNames);
        teamGroupToEntity(baseballTeams, existTeams);

        return teamToDto(existTeams);
    }

    private List<Team> findByTeamName(List<String> teamNames) {
        return teamRepository.findByTeamNameIn(teamNames);
    }

    private List<String> extractTeamName(Elements baseballTeams) {
        return baseballTeams.stream()
                .map(baseballTeam -> new Team().crawledToEntity(baseballTeam).getTeamName())
                .collect(Collectors.toList());
    }

    private void processTeam(Team newTeam, List<Team> existTeams) {
        Optional<Team> teamInDB = existTeams.stream()
                .filter(existing -> existing.getTeamName().equals(newTeam.getTeamName()))
                .findFirst();

        if (teamInDB.isPresent()) {
            Team existTeam = teamInDB.get();
            existTeam.updateTeam(newTeam);
        } else {
            existTeams.add(newTeam);
        }
    }

    public Elements crawlingTeamInfo(){
        try {
            Document doc = Jsoup.connect("https://sports.news.naver.com/kbaseball/record/index.nhn?category=kbo")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36")
                    .get();
            
            Elements baseballTeams = doc.select("#regularTeamRecordList_table > tr");
            
            if(!baseballTeams.isEmpty()){
                return baseballTeams;
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Elements();
    }

    private void teamGroupToEntity(Elements baseballTeams, List<Team> existTeams) {
        for (Element baseballTeam : baseballTeams) {
            Team newTeam = new Team().crawledToEntity(baseballTeam);
            processTeam(newTeam, existTeams);
        }
        teamRepository.saveAll(existTeams);
    }

    public List<TeamInfoResponse> teamToDto(List<Team> existTeams) {
        return existTeams.stream().map(team -> TeamInfoResponse.builder()
                        .teamId(team.getId())
                        .ranking(team.getRanking())
                        .teamName(team.getTeamName())
                        .gamesPlayed(team.getGamesPlayed())
                        .win(team.getWin())
                        .loss(team.getLoss())
                        .draw(team.getDraw())
                        .winRate(team.getWinRate())
                        .consecutive(team.getConsecutive())
                        .last10Games(team.getLast10Games())
                        .build())
                .collect(Collectors.toList());
    }

}
