package study.withkbo.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.notification.entity.Notification;
import study.withkbo.notification.service.NotificationService;
import study.withkbo.security.UserDetailsImpl;
import study.withkbo.team.dto.response.TeamInfoResponseDto;
import study.withkbo.team.entity.Team;
import study.withkbo.team.repository.TeamRepository;
import study.withkbo.user.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    @Scheduled(cron = "0 0 2 * * *")
    public List<TeamInfoResponseDto> selectTeamInfo() {
        Elements baseballTeams = crawlingTeamInfo();
        List<String> teamNames = extractTeamName(baseballTeams);
        List<Team> existTeams = findByTeamName(teamNames);
        teamGroupToEntity(baseballTeams, existTeams);

        return existTeams.stream().map(TeamInfoResponseDto::new).toList();
    }

    private List<Team> findByTeamName(List<String> teamNames) {
        return teamRepository.findByTeamNameIn(teamNames);
    }

    private List<String> extractTeamName(Elements baseballTeams) {
        return baseballTeams.stream()
                .map(baseballTeam -> new Team().crawledToTeamEntity(baseballTeam).getTeamName())
                .toList();
    }

    private void processTeam(Team newTeam, List<Team> existTeams) {
        existTeams.stream()
                .filter(existing -> existing.getTeamName().equals(newTeam.getTeamName()))
                .findFirst()
                .ifPresentOrElse(
                        existingTeam -> existingTeam.updateTeam(newTeam),
                        () -> existTeams.add(newTeam)
                );
    }

    public Elements crawlingTeamInfo() {
        Document doc;
        try {
            doc = Jsoup.connect("https://sports.news.naver.com/kbaseball/record/index.nhn?category=kbo")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36")
                    .get();
        } catch (IOException e) {
            throw new CommonException(CommonError.CRAWLING_ERROR);
        }
        Elements baseballTeams = doc.select("#regularTeamRecordList_table > tr");

        if (baseballTeams.isEmpty()) {
            throw new CommonException(CommonError.NO_TEAM_INFO);
        }

        return baseballTeams;
    }

    private void teamGroupToEntity(Elements baseballTeams, List<Team> existTeams) {
        baseballTeams.forEach(baseballTeam -> {
            Team newTeam = new Team().crawledToTeamEntity(baseballTeam);
            processTeam(newTeam, existTeams);
        });
        saveTeams(existTeams);
    }

    private void saveTeams(List<Team> existTeams) {
        Optional.ofNullable(existTeams)
                .filter(teams -> !teams.isEmpty())
                .ifPresentOrElse(
                        teamRepository::saveAll,
                        () -> {
                            throw new CommonException(CommonError.INTERNAL_SERVER_ERROR);
                        }
                );
    }

}
