package study.withkbo.partypost.service;

import study.withkbo.partypost.dto.response.PartyPostResponseDto;
import study.withkbo.partypost.entity.PartyPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PartyPostService {

//    // 게시글 조회 (ID로)
//    Optional<PartyPost> getPartyPostById(Long postId);
//
//    // 게시글 작성
//    PartyPost createPartyPost(PartyPost partyPost);
//
//    // 게시글 수정
//    PartyPost updatePartyPost(Long postId, PartyPost updatedPartyPost);
//
//    // 게시글 삭제
//    void deletePartyPost(Long postId);
//
//    // 전체 게시글 목록 조회 (페이징)
//    Page<PartyPost> getAllPartyPosts(Pageable pageable);
//
//    // 조건에 맞는 게시글 목록 조회 (검색 기능 포함)
//    Page<PartyPost> searchPartyPosts(String keyword, Pageable pageable);
    // 어.. 이러면 되나?  글 전체 페이지 조회
    Page<PartyPostResponseDto> findAllWithPageable(Pageable pageable);
}
