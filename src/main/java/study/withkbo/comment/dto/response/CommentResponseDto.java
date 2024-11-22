package study.withkbo.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.withkbo.comment.entity.Comment;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    private Long commentId; // 댓글 ID (댓글 삭제할 때 필요)
    private Long userId; // 댓글 작성자의 ID ( 댓글 삭제할 때 작성자와 일치하는지 확인할 때 필요)
    private String userName; // 댓글 작성자의 닉네임
    private String profileImage;   // 작성자의 프로필 이미지 URL
    private String content; // 댓글 내용
    private String createAt; // 댓글 작성일시 (예: "2024-12-01 12:00")

    public static CommentResponseDto fromEntity(Comment comment) {
        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .userId(comment.getUser().getId())
                .userName(comment.getUser().getNickname())
                .profileImage(comment.getUser().getProfileImg())
                .content(comment.getContent())
                .createAt(comment.getCreatedDate().toString())
                .build();
    }
}
