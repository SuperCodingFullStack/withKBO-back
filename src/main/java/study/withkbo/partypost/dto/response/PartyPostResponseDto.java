package study.withkbo.partypost.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.withkbo.comment.dto.response.CommentResponseDto;
import study.withkbo.partypost.entity.PartyPost;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyPostResponseDto {
    /*
    *  id: 1(게시글 아이디)
    *  userNickname: bin (작성자 닉네임)
    *  userImg: 프로필 사진 주소
    *  title: 롯데 경기보러 가실분
    *  content: 야구장 첨가봐요 혼자가기 무서운데 같이 가실분
    *  myTeamImg: 롯데 구단 이미지
    *  opposingTeam: NC 구단 이미지
    *  latitude: 35.19
    *  longitude: 129.06
    *  matchDate: 2024-12-31 18:00
    *  maxPeopleNum: 2
    *  currentPeopleNum: 1
    *  likeCount: 0
    *  hitCount: 10
    *  createAt: 2024-12-01 12:00
    * */

    private Long id; // 해당 게시글의 아이디(게시글 목록에서 클릭 시 이동할 때 필요)
    private String userNickname; // 작성자의 이름 (게시글 안에서 표시될 이름)
    private String userImg; // 작성자의 프로필 이미지 URL

    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private String myTeamImg;
    private String opposingTeam;// 경기 정보와 관련된 이미지 URL
    // 상대팀 이미지
    // 우리팀 이미지
    // 분할 연동

    private String latitude;// 위도
    private String longitude;// 경도
    private String matchDate; // 경기 일정 (예: "2024-12-31 18:00")

    private Integer maxPeopleNum; // 최대 참여 인원
    private Integer currentPeopleNum; // 현재 참여 인원
    private Integer likeCount; // 좋아요 수
    private Integer hitCount; // 조회수
    private String createAt; // 게시글 작성일시 (예: "2024-12-01 12:00")


    @Builder.Default
    private List<CommentResponseDto> comments = List.of(); // 해당 게시글에 달린 댓글들 (댓글 목록)

    public static PartyPostResponseDto fromEntity(PartyPost partyPost) {
        return PartyPostResponseDto.builder()
                .id(partyPost.getId())
                .userNickname(partyPost.getUser().getUNickname()) // 작성자 닉네임
                .userImg(partyPost.getUser().getProfileImg()) // 프로필 이미지
                .title(partyPost.getTitle()) // 게시글 제목
                .content(partyPost.getContent()) // 게시글 내용
//                .myTeamImg(partyPost.getGame().해당하시는 우리팀객체 접근().우리팀이미지 가져오기()) // 우리 팀 이미지
//                .opposingTeam(partyPost.getGame()..해당하는 상대팀객체 접근().상대팀이미지 가져오기()) // 상대팀 정보
//                .latitude(partyPost.getGame().get우리팀 경기구장?().getLatitude()) // 위치 위도
//                .longitude(partyPost.getGame().get상대팀().getLongitude()) // 위치 경도
//                .matchDate(partyPost.getGame().getMatchInfo().toString()) // 경기 일정
                .maxPeopleNum(partyPost.getMaxPeopleNum()) // 최대 참여 인원
                .currentPeopleNum(partyPost.getCurrentPeopleNum()) // 현재 참여 인원
                .likeCount(partyPost.getLikeCount()) // 좋아요 수
                .hitCount(partyPost.getHitCount()) // 조회수
                .createAt(partyPost.getCreatedDate().toString()) // 작성 일시
                .comments(partyPost.getComments().stream()
                        .map(CommentResponseDto::fromEntity) // 댓글 리스트 매핑
                        .toList())
                .build();
    }
}

