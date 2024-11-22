package study.withkbo.partypost.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PartyPostUpdateRequestDto {
    private String title;         // 수정할 글 제목
    private String content;       // 수정할 글 내용
    private Integer maxPeopleNum; // 수정할 최대 모집 인원
    private Long gameId;          // 수정할 경기 ID
}
