package study.withkbo.partypost.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.game.entity.Game;
import study.withkbo.game.repository.GameRepository;
import study.withkbo.partypost.dto.request.PartyPostUpdateRequestDto;
import study.withkbo.partypost.dto.request.PartyPostWriteRequestDto;
import study.withkbo.partypost.dto.response.*;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.partypost.repository.PartyPostRepository;
import study.withkbo.user.entity.User;
import study.withkbo.user.repository.UserRepository;


import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartyPostServiceImpl implements PartyPostService {

    private final PartyPostRepository partyPostRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;


//    @Operation("게시글 아이디로 상세게시글 가져오기")
    @Override
    public PostResponseDto getPartyPostById(Long id) {
        PartyPost partyPost = partyPostRepository.findById(id)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));

        return PostResponseDto.fromEntity(partyPost);  // 엔티티를 DTO로 변환하여 반환
    }

    // 게시글 작성
    @Transactional
    @Override
    public PartyPostWriteResponseDto createPost(PartyPostWriteRequestDto partyPostRequestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));
        Game game = gameRepository.findById(partyPostRequestDto.getGameId())
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));

        PartyPost partyPost = PartyPost.builder()
                .user(user)
                .game(game)
                .title(partyPostRequestDto.getTitle())
                .content(partyPostRequestDto.getContent())
                .maxPeopleNum(partyPostRequestDto.getMaxPeopleNum())
                .currentPeopleNum(1)  // 기본값: 1명
                .likeCount(0)
                .hitCount(0)
                .postState(true)
                .build();

        partyPostRepository.save(partyPost);
        return PartyPostWriteResponseDto.builder()
                .id(partyPost.getId())
                .build();
    }

    // 글을 수정하는 것
    @Transactional
    @Override
    public PartyPostUpdateResponseDto updatePartyPost(Long id, Long userId, PartyPostUpdateRequestDto updateDto) {

        PartyPost post = partyPostRepository.findById(id)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));

        Game game = post.getGame();

        // 글 작성자와 요청한 유저가 같은지 확인
        if (!post.getUser().getId().equals(userId)) {
            throw new CommonException(CommonError.FORBIDDEN);
        }

        // 경기 정보 수정
        if(!game.getId().equals(updateDto.getGameId())) {
             game = gameRepository.findById(updateDto.getGameId())
                    .orElseThrow(() ->new CommonException(CommonError.NOT_FOUND));
        }

        // 기존 post를 기반으로 새로운 인스턴스 생성
        PartyPost updatedPost = post.toBuilderWithUpdates(game, updateDto);
        partyPostRepository.save(updatedPost);
        return PartyPostUpdateResponseDto.builder()
                .id(updatedPost.getId())
                .build();
    }
// 글을 삭제하는 것
    @Override
    public PartyPostDeleteResponseDto deletePartyPost(Long id, Long userId) {

        PartyPost deletePartyPost = partyPostRepository.findById(id)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));

        if (!deletePartyPost.getUser().getId().equals(userId)) {
            throw new CommonException(CommonError.FORBIDDEN);
        }
        partyPostRepository.delete(deletePartyPost);
        return PartyPostDeleteResponseDto.builder()
                .id(deletePartyPost.getId())
                .build();
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

