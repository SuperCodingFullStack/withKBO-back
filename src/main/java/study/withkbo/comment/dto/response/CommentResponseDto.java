package study.withkbo.comment.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long commentId; // 댓글 ID
    private Long userId; // 댓글 작성자의 ID
    private String userName; // 댓글 작성자의 이름
    private String content; // 댓글 내용
    private String createAt; // 댓글 작성일시 (예: "2024-12-01 12:00")
}
