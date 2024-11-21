package study.withkbo.partypost.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
import study.withkbo.friend.dto.response.FriendResponseDto;
import study.withkbo.partypost.dto.response.PartyPostPageResponseDto;
import study.withkbo.partypost.dto.response.PartyPostResponseDto;
import study.withkbo.partypost.dto.response.PostResponseDto;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.partypost.service.PartyPostService;

import java.util.Optional;

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
    public ApiResponseDto<PostResponseDto> getPostById(@PathVariable("id") Long id) {
        // 서비스 호출로 게시물 가져오기
        PostResponseDto result = partyPostService.getPartyPostById(id);

        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

}
