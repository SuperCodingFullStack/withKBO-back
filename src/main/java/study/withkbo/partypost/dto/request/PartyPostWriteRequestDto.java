package study.withkbo.partypost.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyPostWriteRequestDto {
    private Long gameId;  // 경기 ID
    private String title;  // 글 제목
    private String content;  // 글 내용
    private Integer maxPeopleNum;  // 최대 모집 인원
}

