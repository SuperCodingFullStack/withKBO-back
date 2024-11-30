package study.withkbo.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.withkbo.comment.dto.request.CommentRequestDto;
import study.withkbo.comment.dto.response.CommentPageResponseDto;
import study.withkbo.comment.dto.response.CommentResponseDto;
import study.withkbo.comment.service.CommentService;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
import study.withkbo.security.UserDetailsImpl;
import study.withkbo.user.entity.User;


@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    // 댓글 조회 페이지네이션
    @GetMapping("/{postId}")
    public ApiResponseDto<CommentPageResponseDto> getCommentsByPostId(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,  // 기본 페이지는 0
            @RequestParam(defaultValue = "10") int size  // 기본 페이지 크기는 10
    ) {
        // 페이지 번호와 크기 유효성 검사
        if (page < 0) page = 0;
        if (size <= 0) size = 10;

        CommentPageResponseDto result = commentService.getCommentsByPostId(postId, page, size);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    // 댓글 생성
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/{postId}")
    public ApiResponseDto<CommentResponseDto> createComment(@PathVariable("postId") Long postId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 토큰에서 유저만 뜯어서 가져오기
        User user =userDetails.getUser();
         CommentResponseDto result = commentService.createComment(commentRequestDto,user,postId);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

    // 댓글 삭제
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("/{commentId}")
    public ApiResponseDto<Void> deleteComment(@PathVariable("commentId") Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 토큰에서 유저만 뜯어서 가져오기
        User user =userDetails.getUser();

         commentService.deleteComment(commentId, user);
        return ApiResponseDto.success(MessageType.DELETE);
    }
}
