package study.withkbo.partypost.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import study.withkbo.partypost.dto.response.PartyPostResponseDto;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.partypost.repository.PartyPostRepository;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartyPostServiceImpl implements PartyPostService {

    private final PartyPostRepository partyPostRepository;

//    @Override
//    // id를 가지고 게시글을 가져오기.
//    public Optional<PartyPost> getPartyPostById(Long postId) {
//        return partyPostRepository.findById(postId);
//    }
//
//    @Override
//    // 게시글을 작성하기
//    public PartyPost createPartyPost(PartyPost partyPost) {
//        return partyPostRepository.save(partyPost);
//    }
//
//    @Override
//    // 게시글을 수정하기
//    public PartyPost updatePartyPost(Long postId, PartyPost updatedPartyPost) {
//        return partyPostRepository.findById(postId)
//                .map(partyPost -> {
//                    partyPost.setTitle(updatedPartyPost.getTitle());
//                    partyPost.setContent(updatedPartyPost.getContent());
//                    partyPost.setMaxPeopleNum(updatedPartyPost.getMaxPeopleNum());
//                    partyPost.setCurrentPeopleNum(updatedPartyPost.getCurrentPeopleNum());
//                    return partyPostRepository.save(partyPost);
//                }).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
//    }
//
//    @Override
//    public void deletePartyPost(Long postId) {
//        partyPostRepository.deleteById(postId);
//    }
//
//    @Override
//    public Page<PartyPost> getAllPartyPosts(Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public Page<PartyPost> getAllPartyPosts(Pageable pageable) {
//        return partyPostRepository.findAll(pageable);
//    }
//
//    @Override
//    public Page<PartyPost> searchPartyPosts(String keyword, Pageable pageable) {
//        return partyPostRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
//    }

    public Page<PartyPostResponseDto> findAllWithPageable(Pageable pageable) {
        return partyPostRepository.findAll(pageable)
                .map(PartyPostResponseDto::fromEntity);  // MapStruct 사용
    }
}

