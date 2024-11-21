package study.withkbo.partypost.service;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.partypost.dto.response.PartyPostPageResponseDto;
import study.withkbo.partypost.dto.response.PartyPostResponseDto;
import study.withkbo.partypost.dto.response.PostResponseDto;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.partypost.repository.PartyPostRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartyPostServiceImpl implements PartyPostService {

    private final PartyPostRepository partyPostRepository;


//    @Operation("게시글 아이디로 상세게시글 가져오기")
    @Override
    public PostResponseDto getPartyPostById(Long id) {
        PartyPost partyPost = partyPostRepository.findById(id)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));

        return PostResponseDto.fromEntity(partyPost);  // 엔티티를 DTO로 변환하여 반환
    }

    // 페이지네이션을 적용하여 게시글 목록 조회
    public PartyPostPageResponseDto findPartyPostsWithPageable(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PartyPost> partyPostPage = partyPostRepository.findAll(pageable);

        // 게시글 목록을 PartyPostResponseDto로 변환
        List<PartyPostResponseDto> partyPostResponseDtos = partyPostPage.getContent().stream()
                .map(PartyPostResponseDto::fromEntity)  // 엔티티를 DTO로 변환
                .collect(Collectors.toList());

        // 페이지네이션 정보와 게시글 목록을 DTO로 반환
        return PartyPostPageResponseDto.fromPartyPostResponseDto(
                partyPostResponseDtos,
                partyPostPage.getTotalPages(),
                partyPostPage.getTotalElements(),
                partyPostPage.getNumber(),
                partyPostPage.getSize()
        );
    }


}

