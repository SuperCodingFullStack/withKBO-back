package study.withkbo.partypost.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PartyPostUpdateResponseDto {
    private Long id;  // 게시글 ID
}