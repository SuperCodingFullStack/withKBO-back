package study.withkbo.game.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import study.withkbo.game.repository.GameRepository;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Object selectGameInfo() {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = null;

        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("headless");
            driver = new ChromeDriver(options);

            driver.get("https://sports.daum.net/schedule/kbo");
            Document doc = Jsoup.parse(driver.getPageSource());

            Elements gameInfo = doc.select("#scheduleList tr");

            for(Element gameInfoElement : gameInfo){
                String date = gameInfoElement.select("td.td_date").text();
                String time = gameInfoElement.select("td.td_time").text();
                String stadium = gameInfoElement.select("td.td_area").text();
                String homeTeam = gameInfoElement.select("td.td_team div.info_team.team_home").text();
                String awayTeam = gameInfoElement.select("td.td_team div.info_team.team_away").text();
                String sort = gameInfoElement.select("td.td_sort").text();
                String tv = gameInfoElement.select("td.td_tv").text();
                System.out.println(date + " " + time + " " + stadium + " " + homeTeam + " " + awayTeam + " " + sort + " " + tv);

            }

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            if (driver != null) {
                driver.quit();
            }
        }

        return "";
    }
}
