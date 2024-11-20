package study.withkbo.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import study.withkbo.comment.dto.request.CommentDeleteRequestDto;
import study.withkbo.comment.dto.request.CommentRequestDto;
import study.withkbo.comment.dto.response.CommentPageResponseDto;
import study.withkbo.comment.dto.response.CommentResponseDto;
import study.withkbo.comment.service.CommentService;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    // 댓글 조회 페이지네이션
    @GetMapping("/comment/{id}")
    public ApiResponseDto<CommentPageResponseDto> getCommentsByPostId(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,  // 기본 페이지는 0
            @RequestParam(defaultValue = "10") int size  // 기본 페이지 크기는 10
    ) {
        // 페이지 번호와 크기 유효성 검사
        if (page < 0) page = 0;
        if (size <= 0) size = 10;

        CommentPageResponseDto result = commentService.getCommentsByPostId(id, page, size);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    // 댓글 생성
    @PostMapping("/comment/{id}")
    public ApiResponseDto<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto) {
         CommentResponseDto result = commentService.createComment(commentRequestDto);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

    // 댓글 삭제
    @DeleteMapping("/comment/delete")
    public ApiResponseDto<String> deleteComment(@RequestBody CommentDeleteRequestDto deleteRequestDto) {
         String result = commentService.deleteComment(deleteRequestDto);
        return ApiResponseDto.success(MessageType.DELETE, result);
    }
}
