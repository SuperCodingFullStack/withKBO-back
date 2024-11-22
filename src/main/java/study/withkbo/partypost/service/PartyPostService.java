package study.withkbo.partypost.service;

import study.withkbo.partypost.dto.request.PartyPostUpdateRequestDto;
import study.withkbo.partypost.dto.request.PartyPostWriteRequestDto;
import study.withkbo.partypost.dto.response.*;




public interface PartyPostService {

    
    // 게시글 전체 페이지 네이션
    PartyPostPageResponseDto findPartyPostsWithPageable(int page, int size);
    // 특정 게시글 아이디로 가져오기
    PostResponseDto getPartyPostById(Long id);

    // 게시글 작성
    PartyPostWriteResponseDto createPost(PartyPostWriteRequestDto partyPostRequestDto, Long userId);

    // 게시글 수정
    PartyPostUpdateResponseDto updatePartyPost(Long id, Long userId, PartyPostUpdateRequestDto updateDto);

    // 게시글 삭제
    PartyPostDeleteResponseDto deletePartyPost(Long id, Long userId);
}
