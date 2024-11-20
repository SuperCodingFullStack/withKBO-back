package study.withkbo.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {
     private Long userId;           // 작성자 고유 ID
     private Long postId;           // 댓글이 달리는 게시글 고유 ID
     private String profileImage;   // 작성자의 프로필 이미지 URL
     private String nickname;       // 작성자의 닉네임
     private String content;        // 댓글 내용
}
