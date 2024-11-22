package study.withkbo.partypost.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.withkbo.partypost.entity.PartyPost;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyPostResponseDto {
    // 단순 하나의 게시글을 조회할 때 반환하는 정보

    /*
     *  id: 1(글 구분 고유번호)
     *  userNickname: bin (작성자 닉네임)
     *  userImg: 프로필 사진 주소
     *  title: 롯데 경기보러 가실분
     *  myTeamImg: 롯데(앞단에서 이미지 찾는 과정 필요)
     *  opposingTeam: NC(앞단에서 이미지 찾는 과정 필요)
     *  matchDate: 2024-12-31
     *  matchTime: 18:00
     *  maxPeopleNum: 2
     *  currentPeopleNum: 1
     *  likeCount: 0
     *  hitCount: 10
     *  createAt: 2024-12-01 12:00
     * */
    // 프로필 정보
    private Long id; // 해당 게시글 아이디(상세 페이지 이동할 때)
    private String userNickname; // 작성자의 이름 (게시글 안에서 표시될 이름)
    private String userImg; // 작성자의 프로필 이미지 URL
    //글 내용들
    private String title; // 게시글 제목
    private String myTeamImg; // 우리팀 이미지 가져오기 위한 팀이름
    private String opposingTeam;//  상대팀 이미지 가져오기 위한 팀이름
    private String matchDate; // 경기 일정 (예: "2024-12-31"?)
    private String matchTime; // 경기 시간 (18:00)? 인건가?

    // 참여버튼 관련
    private Integer maxPeopleNum; // 최대 참여 인원
    private Integer currentPeopleNum; // 현재 참여 인원
    // 통계관련
    private Integer likeCount; // 좋아요 수
    private Integer hitCount; // 조회수
    //며칠 전 몇 시간 전 표시를 위한
    private String createAt; // 게시글 작성일시 (예: "2024-12-01 12:00")

    public static PartyPostResponseDto fromEntity(PartyPost partyPost) {
        return PartyPostResponseDto.builder()
                .id(partyPost.getId())
                .userNickname(partyPost.getUser().getUNickname()) // 작성자 닉네임
                .userImg(partyPost.getUser().getProfileImg()) // 프로필 이미지
                .title(partyPost.getTitle()) // 게시글 제목
                .myTeamImg(partyPost.getGame().getTeam().getTeamName()) // 우리 팀 이미지 가져오기 위한 팀이름
                .opposingTeam(partyPost.getGame().getAwayTeam()) // 상대팀 이미지 가져오기 위한 상대팀 이름
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
