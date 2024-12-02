package study.withkbo.partypost.dto.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyPostPageResponseDto {
    private List<PartyPostResponseDto> partyPosts;  // 게시글 목록
    private int totalPages;  // 총 페이지 수
    private long totalElements;  // 전체 요소 수
    private int currentPage;  // 현재 페이지
    private int size;// 페이지당 크기
    private Long nextCursor;

    public PartyPostPageResponseDto(List<PartyPostResponseDto> posts, Long nextCursor) {
        this.partyPosts = posts;
        this.nextCursor = nextCursor;
    }

    // PartyPostPageResponseDto 객체를 생성하는 메서드
    public static PartyPostPageResponseDto fromPartyPostResponseDto(
            List<PartyPostResponseDto> partyPostResponseList,
            int totalPages,
            long totalElements,
            int currentPage,
            int size) {

        return PartyPostPageResponseDto.builder()
                .partyPosts(partyPostResponseList)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .currentPage(currentPage)
                .size(size)
                .build();
    }

    public static PartyPostPageResponseDto fromPartyPostResponseDtoWithCursor(
            List<PartyPostResponseDto> posts, Long nextCursor) {
        return new PartyPostPageResponseDto(posts, nextCursor);
    }

}
