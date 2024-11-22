package study.withkbo.partypost.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;

import study.withkbo.partypost.dto.request.PartyPostUpdateRequestDto;
import study.withkbo.partypost.dto.request.PartyPostWriteRequestDto;
import study.withkbo.partypost.dto.response.*;

import study.withkbo.partypost.service.PartyPostService;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class PartyPostController {
    private final PartyPostService partyPostService;

    // 전체 글목록 페이지네이션 지원
    @GetMapping("/posts")
    public ApiResponseDto<PartyPostPageResponseDto> findPartyPosts(
            @RequestParam(defaultValue = "0") int page,  // 기본 페이지 번호는 0
            @RequestParam(defaultValue = "10") int size  // 기본 페이지 크기는 10
    ) {
        // 페이지 번호와 크기 유효성 검사
        if (page < 0) page = 0;
        if (size <= 0) size = 10;

        // 게시글 목록 조회 서비스 호출
        PartyPostPageResponseDto result = partyPostService.findPartyPostsWithPageable(page, size);

        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    // 특정 게시판 id를 가지고 특정 글을 반환하는 것
    @GetMapping("/post/{id}")
    public ApiResponseDto<PostResponseDto> getPostById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {

        private boolean isValidToken(String token) {
            // JWT 유효성 검사 로직
            return true; // 실제 구현 필요
        }

        // JWT에서 userId 추출 (토큰 파싱 로직은 구현된 메서드를 사용)
        Long userId = extractUserIdFromToken(token);

        if (token == null || !isValidToken(token)) {
            throw new UnauthorizedException("Unauthorized: Missing or invalid token");
        }
        // 서비스 호출로 게시물 가져오기
        PostResponseDto result = partyPostService.getPartyPostById(id);

        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    //게시글을 작성하는 것
    @PostMapping("/post")
    public ApiResponseDto<PartyPostWriteResponseDto> createPost(@RequestBody PartyPostWriteRequestDto requestDto, @RequestHeader("Authorization") String token) {

        // JWT에서 userId 추출 (토큰 파싱 로직은 구현된 메서드를 사용)
        Long userId = extractUserIdFromToken(token);

        // 글작성하는 서비스단 작업
        PartyPostWriteResponseDto result = partyPostService.createPost(requestDto, userId);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

    // 수정 요청
    @PutMapping("post/{id}")
    public ApiResponseDto<PartyPostUpdateResponseDto> updatePost(@PathVariable("id") Long id, @RequestBody PartyPostUpdateRequestDto requestDto, @RequestHeader("Authorization") String token) {

        // JWT에서 userId 추출 (토큰 파싱 로직은 구현된 메서드를 사용)
        Long userId = extractUserIdFromToken(token);

        // 글을 수정하는 작업
        PartyPostUpdateResponseDto result = partyPostService.updatePartyPost(id, userId, requestDto);

        return ApiResponseDto.success(MessageType.UPDATE, result);
    }

    // 삭제 요청
    @DeleteMapping("post/{id}")
    public ApiResponseDto<PartyPostDeleteResponseDto> deletePost(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        // JWT에서 userId 추출 (토큰 파싱 로직은 구현된 메서드를 사용)
        Long userId = extractUserIdFromToken(token);
        // 글을 삭제하는 작업
        PartyPostDeleteResponseDto result= partyPostService.deletePartyPost(id, userId);

        return ApiResponseDto.success(MessageType.DELETE, result);
    }

}
