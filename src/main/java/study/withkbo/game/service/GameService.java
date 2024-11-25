package study.withkbo.game.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.game.dto.response.GameInfoResponseDto;
import study.withkbo.game.dto.response.GameResponseDto;
import study.withkbo.game.entity.Game;
import study.withkbo.game.repository.GameRepository;
import study.withkbo.team.entity.Team;
import study.withkbo.team.repository.TeamRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private WebDriver driver;

    @Transactional
    public List<GameInfoResponseDto> selectGameInfo(String month) {
        List<Game> gamesSearchMonth;

        try {
            Element doc = gameInfoCrawling();
            Elements gameInfo = doc.select("#scheduleList tr");
            List<Team> teams = searchTeamNames(gameInfo);
            Map<String, Team> teamMap = stringTeamToMap(teams);
            gameInfoToEntity(gameInfo, teamMap);

        } catch (Exception e) {
            throw new CommonException(CommonError.INTERNAL_SERVER_ERROR);
        }

        gamesSearchMonth = gameRepository.findByMatchDateStartingWith(month);

        if (gamesSearchMonth != null && !gamesSearchMonth.isEmpty()) {
            return gamesSearchMonth.stream().map(GameInfoResponseDto::new).toList();
        } else {
            throw new CommonException(CommonError.GAME_NOT_FOUND);
        }
    }

    @Scheduled(cron = "0 0 2 * * *")
    public Document gameInfoCrawling() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("headless");
            driver = new ChromeDriver(options);
        }

        driver.get("https://sports.daum.net/schedule/kbo");
        return Jsoup.parse(driver.getPageSource());
    }

    private List<Team> searchTeamNames(Elements gameInfo) {
        List<String> homeTeamNames = gameInfo.stream()
                .map(gameInfoElement -> gameInfoElement.select("td.td_team div.info_team.team_home").text().split(" ")[0])
                .toList();
        return teamRepository.findByTeamNameIn(homeTeamNames);
    }

    private Map<String, Team> stringTeamToMap(List<Team> teams) {
        return teams.stream()
                .collect(Collectors.toMap(Team::getTeamName, team -> team));
    }

    @Transactional
    public void gameInfoToEntity(Elements gameInfo, Map<String, Team> teamMap) {
        List<Game> newGames = new ArrayList<>();

        for (Element gameInfoElement : gameInfo) {
            String homeTeamName = getHomeTeamName(gameInfoElement);
            String matchDate = getMatchDate(gameInfoElement);
            String awayTeamName = getAwayTeamName(gameInfoElement);

            Team team = teamMap.get(homeTeamName);

            if (team != null && !isGameAlreadyExists(matchDate, homeTeamName, awayTeamName)) {
                Game newGame = createNewGame(gameInfoElement, team);
                newGames.add(newGame);
                gameRepository.saveAll(newGames);
            }
        }
    }

    private String getHomeTeamName(Element gameInfoElement) {
        return gameInfoElement.select("td.td_team div.info_team.team_home").text().split(" ")[0];
    }

    private String getMatchDate(Element gameInfoElement) {
        return gameInfoElement.select("td.td_date").text();
    }

    private String getAwayTeamName(Element gameInfoElement) {
        return gameInfoElement.select("td.td_team div.info_team.team_away").text().split(" ")[0];
    }

    private boolean isGameAlreadyExists(String matchDate, String homeTeamName, String awayTeamName) {
        return gameRepository.findByMatchDateAndTeam_TeamNameAndAwayTeam(matchDate, homeTeamName, awayTeamName).isPresent();
    }

    private Game createNewGame(Element gameInfoElement, Team team) {
        return new Game().crawledToGameEntity(gameInfoElement, team);
    }

    @Transactional(readOnly = true)
    public List<GameResponseDto> gameInfo(String matchDate) {
        List<Game> games = (gameRepository.findByMatchDateStartingWith(gameMatchDateParser(matchDate)));

        if (games.isEmpty()) {
            throw new CommonException(CommonError.GAME_NOT_FOUND);
        }
        return games.stream().map(GameResponseDto::new).toList();
    }

    private String gameMatchDateParser(String matchDate){
        String date = matchDate.substring(5);
        return date.split("-")[0] + "." + date.split("-")[1];
    }
}
