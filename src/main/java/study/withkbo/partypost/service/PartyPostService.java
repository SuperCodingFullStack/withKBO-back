package study.withkbo.partypost.service;


import study.withkbo.partypost.dto.request.PartyPostUpdateRequestDto;
import study.withkbo.partypost.dto.request.PartyPostWriteRequestDto;
import study.withkbo.partypost.dto.response.*;
import study.withkbo.user.entity.User;
import java.util.List;


public interface PartyPostService {

    // 특정 게시글 아이디로  상세 게시글 가져오기
    PostResponseDto getPartyPostById(Long id);
    // 게시글 작성
    PartyPostWriteResponseDto createPost(PartyPostWriteRequestDto partyPostRequestDto,User user);
    // 게시글 수정
    PartyPostUpdateResponseDto updatePartyPost(Long id, User user, PartyPostUpdateRequestDto updateDto);
    // 게시글 삭제
    PartyPostDeleteResponseDto deletePartyPost(Long id, User user);


    // 게시글 커서기반 페이징 조건? 필터? 페이지네이션?
    PartyPostPageResponseDto getPartyPostsWithCursor(String teamName, Long gameId, Long cursor, int size, String[] sortBy, boolean ascending);

    // 경민님 특정 마이페이지 글 조회
    List<PartyPostMyPageResponseDto> findMyPostsByType(String type, User user);
    // 메인에서 특정유저가 좋아요 누른것만 가져와 조회하기
    PartyPostPageResponseDto getLikedPosts(User user, String teamName, Long gameId, String[] sortBy, boolean ascending, Long cursor, int size);
    // 메인에서 내가 작성한 글들만 조회하기
    PartyPostPageResponseDto getMyPostsWithCursor(User user, String teamName, Long gameId, String[] sortBy, boolean ascending, Long cursor, int size);

}

