package study.withkbo.partypost.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.withkbo.partypost.entity.PartyPost;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {
    // 단순 하나의 게시글을 조회할 때 반환하는 정보

    /*
     *  id: 1(작성자 구분고유번호)
     *  userNickname: bin (작성자 닉네임)

     *  userImg: 프로필 사진 주소
     *  title: 롯데 경기보러 가실분
     *  content: 야구장 첨가봐요 혼자가기 무서운데 같이 가실분
     *  myTeamImg: 롯데(앞단에서 이미지 찾는 과정 필요)
     *  opposingTeam: NC(앞단에서 이미지 찾는 과정 필요)
     *  latitude: 35.19
     *  longitude: 129.06
     *  matchDate: 2024-12-31
     *  matchTime: 18:00
     *  maxPeopleNum: 2
     *  currentPeopleNum: 1
     *  likeCount: 0
     *  hitCount: 10
     *  createAt: 2024-12-01 12:00
     * */
    // 프로필 정보
    private Long id; // 해당 게시글의  작성자 아이디(작성자 상세 프로필? 모달 띄울 때)
    private String userNickname; // 작성자의 이름 (게시글 안에서 표시될 이름)
    private String userImg; // 작성자의 프로필 이미지 URL
    //글 내용들
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private String myTeamImg; // 우리팀 이미지 가져오기 위한 팀이름
    private String opposingTeam;//  상대팀 이미지 가져오기 위한 팀이름
    private String matchDate; // 경기 일정 (예: "2024-12-31"?)
    private String matchTime; // 경기 시간 (18:00)? 인건가?
    // 지도 세팅
    private String latitude;// 위도
    private String longitude;// 경도
    // 참여버튼 관련
    private Integer maxPeopleNum; // 최대 참여 인원
    private Integer currentPeopleNum; // 현재 참여 인원
    // 통계관련
    private Integer likeCount; // 좋아요 수
    private Integer hitCount; // 조회수
    //며칠 전 몇 시간 전 표시를 위한
    private String createAt; // 게시글 작성일시 (예: "2024-12-01 12:00")

    public static PostResponseDto fromEntity(PartyPost partyPost) {
        return PostResponseDto.builder()
                .id(partyPost.getUser().getId())
                .userNickname(partyPost.getUser().getNickname()) // 작성자 닉네임
                .userImg(partyPost.getUser().getProfileImg()) // 프로필 이미지
                .title(partyPost.getTitle()) // 게시글 제목
                .content(partyPost.getContent()) // 게시글 내용
                .myTeamImg(partyPost.getGame().getTeam().getTeamName()) // 우리 팀 이미지 가져오기 위한 팀이름
                .opposingTeam(partyPost.getGame().getAwayTeam()) // 상대팀 이미지 가져오기 위한 상대팀 이름
                // 지도를 위한 위도 경도 가져오기
                .latitude(partyPost.getGame().getTeam().getLatitude().toString()) // 우리팀? 경기장 위치 위도 문자열로
                .longitude(partyPost.getGame().getTeam().getLongitude().toString()) // 우리팀? 경기장 위치 경도 문자열로
                .matchDate(partyPost.getGame().getMatchDate()) // 경기 일정
                .matchDate(partyPost.getGame().getMatchTime()) // 경기 시간
                .maxPeopleNum(partyPost.getMaxPeopleNum()) // 최대 참여 인원
                .currentPeopleNum(partyPost.getCurrentPeopleNum()) // 현재 참여 인원
                .likeCount(partyPost.getLikeCount()) // 좋아요 수
                .hitCount(partyPost.getHitCount()) // 조회수
                .createAt(partyPost.getCreatedDate().toString()) // 작성 일시
                .build();
    }
}
