package study.withkbo.partypost.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.withkbo.comment.dto.response.CommentResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyPostResponseDto {

//    private Long id; // 해당 게시글의 아이디(게시글 목록에서 클릭 시 이동할 때 필요)
    private String userNickname; // 작성자의 이름 (게시글 안에서 표시될 이름)
    private String userImg; // 작성자의 프로필 이미지 URL

//    private String title; // 게시글 제목
//    private String content; // 게시글 내용
//    private String matchImg; // 경기 정보와 관련된 이미지 URL
    // 상대팀 이미지
    // 우리팀 이미지
    // 분할 연동

//    private String matchLoc; // 경기 장소 (위도, 경도 값, 지도 API 표시용)
    // 경기장 이름에 스트링으로 받고 앞단에서 위도와 경도를 따로 저장한 뒤 이름을 가지고 호출한다.
    //위도
    //경도 따로 받기

//    private String matchDate; // 경기 일정 (예: "2024-12-31 18:00")

//    private Integer maxPeopleNum; // 최대 참여 인원
//    private Integer currentPeopleNum; // 현재 참여 인원

//    private Integer likeCount; // 좋아요 수
//    private Integer hitCount; // 조회수

    private String createAt; // 게시글 작성일시 (예: "2024-12-01 12:00")

    @Builder.Default
    private List<CommentResponseDto> comments = List.of(); // 해당 게시글에 달린 댓글들 (댓글 목록)
}
