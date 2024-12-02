package study.withkbo.partypost.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;

import study.withkbo.partypost.dto.request.PartyPostUpdateRequestDto;
import study.withkbo.partypost.dto.request.PartyPostWriteRequestDto;
import study.withkbo.partypost.dto.response.*;

import study.withkbo.partypost.service.PartyPostService;
import study.withkbo.security.UserDetailsImpl;
import study.withkbo.user.entity.User;

import java.util.List;


@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Slf4j
public class PartyPostController {
    private final PartyPostService partyPostService;

    // 전체 글목록 페이지네이션 지원
    @GetMapping("s")
    public ApiResponseDto<PartyPostPageResponseDto> findPartyPosts(
            @RequestParam(defaultValue = "0") int page,  // 기본 페이지 번호는 0
            @RequestParam(defaultValue = "10") int size,  // 기본 페이지 크기는 10
            @RequestParam(value = "teamName", required = false) String teamName, // 팀이름
            @RequestParam(value = "gameId", required = false) Long gameId, // 게임 id
            @RequestParam(value = "sortyBy", required = false) String[] sortBy, // 정렬 기준들
            @RequestParam(defaultValue = "false") boolean ascending // 기본은 내림차순
            ) {

        // 페이지 번호와 크기 유효성 검사
        if (page < 0) page = 0;
        if (size <= 0) size = 10;


        // 게시글 목록 조회 서비스 호출
        PartyPostPageResponseDto result = partyPostService.getPartyPosts(teamName, gameId, page, size, sortBy, ascending);

        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    // 특정 게시판 id를 가지고 특정 글을 반환하는 것
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{postId}")
    public ApiResponseDto<PostResponseDto> getPostById(@PathVariable("postId") Long postId) {

        // 서비스 호출로 게시물 가져오기
        PostResponseDto result = partyPostService.getPartyPostById(postId);

        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    //게시글을 작성하는 것
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("")
    public ApiResponseDto<PartyPostWriteResponseDto> createPost( @RequestBody PartyPostWriteRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // 토큰에서 유저만 뜯어서 가져오기
        User user =userDetails.getUser();

        // 글작성하는 서비스단 작업
        PartyPostWriteResponseDto result = partyPostService.createPost(requestDto, user);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

    // 수정 요청
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/{id}")
    public ApiResponseDto<PartyPostUpdateResponseDto> updatePost(@PathVariable("id") Long id, @RequestBody PartyPostUpdateRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // 토큰에서 유저만 뜯어서 가져오기
        User user =userDetails.getUser();

        // 글을 수정하는 작업
        PartyPostUpdateResponseDto result = partyPostService.updatePartyPost(id, user, requestDto);

        return ApiResponseDto.success(MessageType.UPDATE, result);
    }

    // 삭제 요청
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or (hasAuthority('ROLE_USER') and #userDetails.user.id == #id)")
    @DeleteMapping("/{id}")
    public ApiResponseDto<PartyPostDeleteResponseDto> deletePost(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 토큰에서 유저만 뜯어서 가져오기
        User user =userDetails.getUser();

        // 글을 삭제하는 작업
        PartyPostDeleteResponseDto result= partyPostService.deletePartyPost(id, user);

        return ApiResponseDto.success(MessageType.DELETE, result);
    }

    // 마이페이지에서의 특정 조건 리스트 반환
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/myList/{type}")
    public ApiResponseDto<List<PartyPostMyPageResponseDto>> getMyList(@PathVariable("type") String type, @AuthenticationPrincipal UserDetailsImpl userDetails ) {
        // 토큰에서 유저만 뜯어서 가져오기
        User user =userDetails.getUser();

        //글을 조회해오는 작업
        List<PartyPostMyPageResponseDto> result = partyPostService.findMyPostsByType(type, user);

        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    // 메인페이지에서 특정유저가 좋아요를 누른글만 조회하고자 할 때 사용
    @GetMapping("/likedPosts")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ApiResponseDto<PartyPostPageResponseDto> findLikedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 토큰에서 유저만 뜯어서 가져오기
        User user =userDetails.getUser();


        // 좋아요를 누른 글 조회 서비스 호출
        PartyPostPageResponseDto result = partyPostService.getLikedPosts(user, page, size);

        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    // 메인에서 자기가 작성한 글들만 조회하려고 할 때
    @GetMapping("/myPosts")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ApiResponseDto<PartyPostPageResponseDto> findMyPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 토큰에서 유저 정보 가져오기
        User user = userDetails.getUser();

        // 사용자가 작성한 글 조회 서비스 호출
        PartyPostPageResponseDto result = partyPostService.getMyPosts(user, page, size);

        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }


}
