package study.withkbo.team.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.jsoup.nodes.Element;
import study.withkbo.game.entity.Game;
import jakarta.persistence.*;
import study.withkbo.player.entity.Player;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_team")
public class Team {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Game> games;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Player> players;

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
    private Double latitude;
    private Double longitude;
    private String logoImg;

    public Team crawledToTeamEntity(Element baseballTeam) {
        this.ranking = Integer.parseInt(getTextSafe(baseballTeam.selectFirst("th")));
        this.teamName = getTextSafe(baseballTeam.selectFirst("span:nth-child(2)"));
        this.gamesPlayed = Integer.parseInt(getTextSafe(baseballTeam.selectFirst("td:nth-child(3)")));
        this.win = Integer.parseInt(getTextSafe(baseballTeam.selectFirst("td:nth-child(4)")));
        this.loss = Integer.parseInt(getTextSafe(baseballTeam.selectFirst("td:nth-child(5)")));
        this.draw = Integer.parseInt(getTextSafe(baseballTeam.selectFirst("td:nth-child(6)")));
        this.winRate = Float.valueOf(getTextSafe(baseballTeam.selectFirst("td:nth-child(7)")));
        this.consecutive = getTextSafe(baseballTeam.selectFirst("td:nth-child(9)"));
        this.last10Games = getTextSafe(baseballTeam.selectFirst("td:nth-child(12)"));
        this.stadium = StadiumByTeamName(getTextSafe(baseballTeam.selectFirst("span:nth-child(2)")));

        return this;
    }

    private String getTextSafe(Element element) {
        return (element != null && !element.text().isEmpty()) ? element.text() : "정보 확인 중 입니다.";
    }

    public void updateTeam(Team newTeam) {
        this.ranking = newTeam.getRanking();
        this.teamName = newTeam.getTeamName();
        this.gamesPlayed = newTeam.getGamesPlayed();
        this.win = newTeam.getWin();
        this.loss = newTeam.getLoss();
        this.draw = newTeam.getDraw();
        this.winRate = newTeam.getWinRate();
        this.consecutive = newTeam.getConsecutive();
        this.last10Games = newTeam.getLast10Games();
    }

    private String StadiumByTeamName(String teamName) {
        switch (teamName) {
            case "KIA":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/KIA_LOGO.png";
                this.latitude = 35.1606;
                this.longitude = 126.8535;
                return "광주-KIA 챔피언스필드";
            case "두산":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/DOOSAN_LOGO.png";
                this.latitude = 37.5126;
                this.longitude = 127.0712;
                return "잠실 야구장";
            case "삼성":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/SAMSUNG_LOGO.png";
                this.latitude = 35.8676;
                this.longitude = 128.6014;
                return "대구 삼성라이온즈 파크";
            case "LG":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/LG_LOGO.png";
                this.latitude = 37.5126;
                this.longitude = 127.0712;
                return "잠실 야구장";
            case "키움":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/KIWOOM_LOGO.png";
                this.latitude = 37.5267;
                this.longitude = 126.9028;
                return "고척스카이돔";
            case "SSG":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/SSG_LOGO.png";
                this.latitude = 37.4550;
                this.longitude = 126.7045;
                return "인천 SSG 랜더스 필드";
            case "한화":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/HANWHA_LOGO.png";
                this.latitude = 36.3506;
                this.longitude = 127.3849;
                return "대전 한화생명 이글스 파크";
            case "롯데":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/LOTTE_LOGO.png";
                this.latitude = 35.1453;
                this.longitude = 129.0584;
                return "사직 야구장";
            case "NC":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/NC_LOGO.png";
                this.latitude = 35.2350;
                this.longitude = 128.6826;
                return "창원 NC파크";
            case "KT":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/KT_LOGO.png";
                this.latitude = 37.2647;
                this.longitude = 127.0086;
                return "수원 KT 위즈 파크";
            default:
                this.logoImg = "";
                this.latitude = null;
                this.longitude = null;
                return "미정";
        }
    }
}
