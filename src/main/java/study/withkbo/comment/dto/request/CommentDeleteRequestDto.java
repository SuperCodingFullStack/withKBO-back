package study.withkbo.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDeleteRequestDto {
    private Long userId;    // 요청을 보낸 유저의 고유 ID
    private Long commentId; // 삭제할 댓글의 고유 ID
    private Long postId;    // 댓글이 속한 게시글의 고유 ID
}
