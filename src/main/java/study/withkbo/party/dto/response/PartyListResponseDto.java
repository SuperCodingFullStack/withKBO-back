package study.withkbo.party.dto.response;

import study.withkbo.party.entity.Party;

public class PartyListResponseDto {
//    “partyId” : 10,
//            “partPostId”:3,
//            “partyTitle”: “11/13(수) 롯데 vs 한화 경기 같이 보러가실분”,
//            “gameDate”: 2024-11-13,
//            “gameTime”: 18:30:00,
//            “userNick”: “고감자”,
//            “stadiumName”: “부산 사직 야구장”,
//            “state”: “수락”

    private Long partyId;

    private Long partyPostId;

    private String partyTitle;

    private String gameDate;

    private String gameTime;

    private String userNick;

    private String stadiumName;

    private String state;

    public PartyListResponseDto(Party party){
        this.partyId = party.getId();
        this.partyPostId = party.getPartyPost().getId();
        this.partyTitle = party.getPartyPost().getTitle();
        //partyPost 매핑 이후 구현
        this.gameDate = null;
        this.gameTime = null;

        this.userNick = party.getUser().getUNickname();
        //여기도 매핑 이후 구현
        this.stadiumName = null;
        this.state = party.getAccept() ? "수락": "미수락";
    }


}
