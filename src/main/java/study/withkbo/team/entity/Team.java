package study.withkbo.team.entity;

import lombok.*;
import org.jsoup.nodes.Element;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
    @Column(precision = 10, scale = 6)
    private BigDecimal latitude;
    @Column(precision = 10, scale = 6)
    private BigDecimal longitude;
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
                this.latitude = new BigDecimal("35.168159").setScale(6, RoundingMode.HALF_UP);
                this.longitude =new BigDecimal("126.889104").setScale(6, RoundingMode.HALF_UP);
                return "광주-KIA 챔피언스필드";
            case "두산":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/DOOSAN_LOGO.png";
                this.latitude = new BigDecimal("37.512255").setScale(6, RoundingMode.HALF_UP);
                this.longitude = new BigDecimal("127.071881").setScale(6, RoundingMode.HALF_UP);
                return "잠실 야구장";
            case "삼성":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/SAMSUNG_LOGO.png";
                this.latitude = new BigDecimal("35.841353").setScale(6, RoundingMode.HALF_UP);
                this.longitude = new BigDecimal("128.681570").setScale(6, RoundingMode.HALF_UP);
                return "대구 삼성라이온즈 파크";
            case "LG":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/LG_LOGO.png";
                this.latitude = new BigDecimal("37.512220").setScale(6, RoundingMode.HALF_UP);
                this.longitude = new BigDecimal("127.071848").setScale(6, RoundingMode.HALF_UP);
                return "잠실 야구장";
            case "키움":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/KIWOOM_LOGO.png";
                this.latitude = new BigDecimal("37.498981").setScale(6, RoundingMode.HALF_UP);
                this.longitude = new BigDecimal("126.867084").setScale(6, RoundingMode.HALF_UP);
                return "고척스카이돔";
            case "SSG":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/SSG_LOGO.png";
                this.latitude = new BigDecimal("37.437247").setScale(6, RoundingMode.HALF_UP);
                this.longitude = new BigDecimal("126.693251").setScale(6, RoundingMode.HALF_UP);
                return "인천 SSG 랜더스 필드";
            case "한화":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/HANWHA_LOGO.png";
                this.latitude = new BigDecimal("36.317321").setScale(6, RoundingMode.HALF_UP);
                this.longitude = new BigDecimal("127.429092").setScale(6, RoundingMode.HALF_UP);
                return "대전 한화생명 이글스 파크";
            case "롯데":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/LOTTE_LOGO.png";
                this.latitude = new BigDecimal("35.194224").setScale(6, RoundingMode.HALF_UP);
                this.longitude = new BigDecimal("129.061465").setScale(6, RoundingMode.HALF_UP);
                return "사직 야구장";
            case "NC":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/NC_LOGO.png";
                this.latitude = new BigDecimal("35.222833").setScale(6, RoundingMode.HALF_UP);
                this.longitude = new BigDecimal("128.582240").setScale(6, RoundingMode.HALF_UP);
                return "창원 NC파크";
            case "KT":
                this.logoImg = "https://mymusinsabucket.s3.ap-northeast-2.amazonaws.com/KT_LOGO.png";
                this.latitude = new BigDecimal("37.299960").setScale(6, RoundingMode.HALF_UP);
                this.longitude = new BigDecimal("127.009658").setScale(6, RoundingMode.HALF_UP);
                return "수원 KT 위즈 파크";
            default:
                this.logoImg = "";
                this.latitude = null;
                this.longitude = null;
                return "미정";
        }
    }
}
