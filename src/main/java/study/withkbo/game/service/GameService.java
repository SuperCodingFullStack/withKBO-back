package study.withkbo.game.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

import java.time.Duration;
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

        // 데이터베이스에서 해당 월의 게임 정보 조회
        gamesSearchMonth = gameRepository.findByMatchDateStartingWith(month);

        // 이미 데이터베이스에 게임 정보가 있는 경우 반환
        if (!gamesSearchMonth.isEmpty()) {
            return gamesSearchMonth.stream().map(GameInfoResponseDto::new).collect(Collectors.toList());
        }

        try {
            // 웹 크롤링을 통해 게임 정보 가져오기
            Element doc = gameInfoCrawling(month);
            Elements gameInfo = doc.select("#scheduleList tr");
            List<Team> teams = searchTeamNames(gameInfo);

            // 중복된 팀 제거
            teams = removeDuplicateTeams(teams);

            // 중복 제거된 팀 목록을 Map으로 변환
            Map<String, Team> teamMap = stringTeamToMap(teams);

            // 크롤링한 정보를 게임 엔티티로 변환하여 저장
            gameInfoToEntity(gameInfo, teamMap);

        } catch (Exception e) {
            // 크롤링 중 오류 발생 시 로그 출력 및 예외 처리
            log.error("게임 정보 크롤링 중 오류 발생: {}", e.getMessage());
            throw new CommonException(CommonError.INTERNAL_SERVER_ERROR);
        }

        // 데이터베이스에 새로 추가된 게임 정보 조회
        gamesSearchMonth = gameRepository.findByMatchDateStartingWith(month);

        // 크롤링 후에도 게임 정보가 없는 경우 예외 발생
        if (gamesSearchMonth.isEmpty()) {
            throw new CommonException(CommonError.GAME_NOT_FOUND);
        }

        // 최종적으로 가져온 게임 정보를 DTO로 변환하여 반환
        return gamesSearchMonth.stream().map(GameInfoResponseDto::new).collect(Collectors.toList());
    }

    public Document gameInfoCrawling(String month) {
        // WebDriver 초기화 및 설정
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("headless");
            driver = new ChromeDriver(options);
        }

        // 크롤링할 URL 생성 및 접근
        String url = "https://sports.daum.net/schedule/kbo?date=2024" + month;
        driver.get(url);

        // 해당 요소가 로드될 때까지 대기
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#scheduleList tr")));

        // 페이지 소스 파싱 후 반환
        return Jsoup.parse(driver.getPageSource());
    }

    private List<Team> searchTeamNames(Elements gameInfo) {
        // 홈 팀 이름을 추출하여 목록 생성
        List<String> homeTeamNames = gameInfo.stream()
                .map(gameInfoElement -> gameInfoElement.select("td.td_team div.info_team.team_home").text().split(" ")[0])
                .collect(Collectors.toList());

        // 팀 이름을 기준으로 팀 정보 조회
        return teamRepository.findByTeamNameIn(homeTeamNames);
    }

    // 중복된 팀 객체를 제거하는 메서드
    private List<Team> removeDuplicateTeams(List<Team> teams) {
        return new ArrayList<>(teams.stream()
                .collect(Collectors.toMap(
                        Team::getTeamName, // 키로 팀 이름을 사용
                        team -> team, // 값을 팀 객체로 설정
                        (existing, replacement) -> existing // 중복 키가 발견되면 기존 값을 유지
                ))
                .values()); // List로 변환
    }

    private Map<String, Team> stringTeamToMap(List<Team> teams) {
        // 팀 이름을 키로 하는 Map 생성
        return teams.stream()
                .collect(Collectors.toMap(
                        Team::getTeamName, // 키로 팀 이름을 사용
                        team -> team, // 값을 팀 객체로 설정
                        (existing, replacement) -> existing // 중복 키가 발견되면 기존 값을 유지
                ));
    }

    @Transactional
    public void gameInfoToEntity(Elements gameInfo, Map<String, Team> teamMap) {
        List<Game> newGames = new ArrayList<>();
        String lastMatchDate = "";

        // 게임 정보를 파싱하여 Game 엔티티 생성
        for (Element gameInfoElement : gameInfo) {
            String homeTeamName = getHomeTeamName(gameInfoElement);
            String matchDate = getMatchDate(gameInfoElement);
            String awayTeamName = getAwayTeamName(gameInfoElement);

            // matchDate가 비어있는 경우 이전 날짜 사용
            if (matchDate.isEmpty()) {
                matchDate = lastMatchDate;
            } else {
                lastMatchDate = matchDate;
            }

            Team team = teamMap.get(homeTeamName);

            // 게임이 이미 존재하지 않는 경우 새로운 게임 엔티티 생성
            if (team != null && !isGameAlreadyExists(matchDate, homeTeamName, awayTeamName)) {
                Game newGame = createNewGame(gameInfoElement, team, matchDate);
                newGames.add(newGame);
            }
        }

        // 새로 생성된 게임 정보를 데이터베이스에 저장
        gameRepository.saveAll(newGames);
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
        // 동일한 날짜, 홈 팀, 원정 팀으로 검색하여 중복 여부 확인
        return gameRepository.findByMatchDateAndTeam_TeamNameAndAwayTeam(matchDate, homeTeamName, awayTeamName).isPresent();
    }

    private Game createNewGame(Element gameInfoElement, Team team, String matchDate) {
        // 크롤링한 정보를 바탕으로 새로운 Game 엔티티 생성
        return new Game().crawledToGameEntity(gameInfoElement, team, matchDate);
    }

    @Transactional(readOnly = true)
    public List<GameResponseDto> gameInfo(String matchDate) {
        List<Game> games = gameRepository.findByMatchDateStartingWith(gameMatchDateParser(matchDate));

        if (games.isEmpty()) {
            throw new CommonException(CommonError.GAME_NOT_FOUND);
        }
        return games.stream().map(GameResponseDto::new).collect(Collectors.toList());
    }

    private String gameMatchDateParser(String matchDate) {
        String date = matchDate.substring(5);
        return date.split("-")[0] + "." + date.split("-")[1];
    }
}
