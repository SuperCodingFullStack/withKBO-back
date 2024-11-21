package study.withkbo.comment.dto.response;

import lombok.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentPageResponseDto {
    private List<CommentResponseDto> comments;
    private int totalPages;
    private long totalElements;
    private int currentPage;
    private int pageSize;

    // 댓글 목록과 페이지 정보를 기반으로 CommentPageResponseDto 객체를 생성하는 메서드
    public static CommentPageResponseDto fromCommentResponseDto(
            List<CommentResponseDto> commentResponseList,
            int totalPages,
            long totalElements,
            int currentPage,
            int pageSize) {

        return CommentPageResponseDto.builder()
                .comments(commentResponseList)  // 댓글 목록
                .totalPages(totalPages)         // 총 페이지 수
                .totalElements(totalElements)   // 전체 요소 수
                .currentPage(currentPage)       // 현재 페이지
                .pageSize(pageSize)             // 페이지당 크기
                .build();
    }
}
