package study.withkbo.partypost.service;

import study.withkbo.partypost.dto.response.PartyPostPageResponseDto;
import study.withkbo.partypost.dto.response.PartyPostResponseDto;
import study.withkbo.partypost.dto.response.PostResponseDto;
import study.withkbo.partypost.entity.PartyPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PartyPostService {

    
    // 게시글 전체 페이지 네이션
    PartyPostPageResponseDto findPartyPostsWithPageable(int page, int size);
    // 특정 게시글 아이디로 가져오기
    PostResponseDto getPartyPostById(Long id);
}
