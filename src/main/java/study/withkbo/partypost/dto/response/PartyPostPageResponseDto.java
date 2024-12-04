package study.withkbo.partypost.dto.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@Builder
public class PartyPostPageResponseDto {
    private List<PartyPostResponseDto> partyPosts;  // 게시글 목록
    private Long nextCursor;  // 다음 커서

    public PartyPostPageResponseDto(List<PartyPostResponseDto> posts, Long nextCursor) {
        this.partyPosts = posts;
        this.nextCursor = nextCursor;
    }

    // 커서 기반 응답을 생성하는 메서드
    public static PartyPostPageResponseDto fromPartyPostResponseDtoWithCursor(
            List<PartyPostResponseDto> partyPostResponseList,
            Long nextCursor) {
        return new PartyPostPageResponseDto(partyPostResponseList, nextCursor);
    }
}
