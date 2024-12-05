package study.withkbo.partypost.controller;

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
    private static final int MAX_SIZE = 100; // 최대 페이지 크기

    // 특정 게시판 id를 가지고 특정 글을 반환하는 것
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{postId}")
    public ApiResponseDto<PostResponseDto> getPostById(@PathVariable("postId") Long postId) {
        PostResponseDto result = partyPostService.getPartyPostById(postId);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    // 게시글을 작성하는 것
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("")
    public ApiResponseDto<PartyPostWriteResponseDto> createPost(
            @RequestBody PartyPostWriteRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        PartyPostWriteResponseDto result = partyPostService.createPost(requestDto, user);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

    // 수정 요청
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/{id}")
    public ApiResponseDto<PartyPostUpdateResponseDto> updatePost(
            @PathVariable("id") Long id,
            @RequestBody PartyPostUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        PartyPostUpdateResponseDto result = partyPostService.updatePartyPost(id, user, requestDto);
        return ApiResponseDto.success(MessageType.UPDATE, result);
    }

    // 삭제 요청
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ApiResponseDto<PartyPostDeleteResponseDto> deletePost(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        PartyPostDeleteResponseDto result = partyPostService.deletePartyPost(id, user);
        return ApiResponseDto.success(MessageType.DELETE, result);
    }

    // 전체 글목록 커서 기반 페이지네이션 지원 (id 커서)
    @GetMapping("/s")
    public ApiResponseDto<PartyPostPageResponseDto> findPartyPosts(
            @RequestParam(value = "teamName", required = false) String teamName,
            @RequestParam(value = "gameId", required = false) Long gameId,
            @RequestParam(value = "sortBy", required = false, defaultValue = "") String[] sortBy,
            @RequestParam(defaultValue = "false") boolean ascending,
            @RequestParam(value = "cursor", required = false) Long cursor, // id 타입으로 변경
            @RequestParam(defaultValue = "10") int size) {

        // size의 최대값 제한
        size = Math.min(size, MAX_SIZE);

        PartyPostPageResponseDto result = partyPostService.getPartyPostsWithCursor(teamName, gameId, cursor, size, sortBy, ascending);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    // 마이페이지에서의 특정 조건 리스트 반환
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/myList/{type}")
    public ApiResponseDto<List<PartyPostMyPageResponseDto>> getMyList(
            @PathVariable("type") String type,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<PartyPostMyPageResponseDto> result = partyPostService.findMyPostsByType(type, user);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    // 메인페이지에서 특정 유저가 좋아요를 누른 글만 조회할 때 사용 (커서 기반 페이지네이션 및 필터 적용)
    @GetMapping("/likedPosts")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ApiResponseDto<PartyPostPageResponseDto> findLikedPosts(
            @RequestParam(value = "teamName", required = false) String teamName,
            @RequestParam(value = "gameId", required = false) Long gameId,
            @RequestParam(value = "sortBy", required = false, defaultValue = "createdDate") String[] sortBy,
            @RequestParam(defaultValue = "false") boolean ascending,
            @RequestParam(value = "cursor", required = false) Long cursor, // id 타입으로 변경
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        size = Math.min(size, MAX_SIZE);
        PartyPostPageResponseDto result = partyPostService.getLikedPosts(user, teamName, gameId, sortBy, ascending, cursor, size);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    // 메인에서 자기가 작성한 글들만 조회하려고 할 때 (커서 기반 페이지네이션 및 필터 적용)
    @GetMapping("/myPosts")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ApiResponseDto<PartyPostPageResponseDto> findMyPosts(
            @RequestParam(value = "teamName", required = false) String teamName,
            @RequestParam(value = "gameId", required = false) Long gameId,
            @RequestParam(value = "sortBy", required = false, defaultValue = "createdDate") String[] sortBy,
            @RequestParam(defaultValue = "false") boolean ascending,
            @RequestParam(value = "cursor", required = false) Long cursor, // id 타입으로 변경
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        size = Math.min(size, MAX_SIZE);
        PartyPostPageResponseDto result = partyPostService.getMyPostsWithCursor(user, teamName, gameId, sortBy, ascending, cursor, size);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }
}
