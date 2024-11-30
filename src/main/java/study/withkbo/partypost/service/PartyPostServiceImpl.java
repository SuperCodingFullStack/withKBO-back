package study.withkbo.partypost.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.game.entity.Game;
import study.withkbo.game.repository.GameRepository;
import study.withkbo.like.entity.Like;
import study.withkbo.like.repository.LikeRepository;
import study.withkbo.partypost.Specification.PartyPostSpecification;
import study.withkbo.partypost.dto.request.PartyPostUpdateRequestDto;
import study.withkbo.partypost.dto.request.PartyPostWriteRequestDto;
import study.withkbo.partypost.dto.response.*;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.partypost.repository.PartyPostRepository;
import study.withkbo.user.entity.User;
import study.withkbo.user.entity.UserRoleEnum;


import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyPostServiceImpl implements PartyPostService {

    private final PartyPostRepository partyPostRepository;
    private final GameRepository gameRepository;
    private final LikeRepository likeRepository;


    //    @Operation("게시글 아이디로 상세게시글 가져오기")
    @Transactional
    @Override
    public PostResponseDto getPartyPostById(Long id) {
        PartyPost partyPost = partyPostRepository.findById(id)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));
      log.info("getPartyPostById: {}", partyPost);

        return PostResponseDto.fromEntity(partyPost);  // 엔티티를 DTO로 변환하여 반환
    }

    // 게시글 작성

    @Override
    @Transactional
    public PartyPostWriteResponseDto createPost(PartyPostWriteRequestDto partyPostRequestDto, User user) {

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
    public PartyPostUpdateResponseDto updatePartyPost(Long id, User user, PartyPostUpdateRequestDto updateDto) {

        PartyPost post = partyPostRepository.findById(id)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));

        Game game = post.getGame();

        // 글 작성자와 요청한 유저가 같은지 확인
        if (!post.getUser().getId().equals(user.getId()) || !user.getRole().equals(UserRoleEnum.ADMIN)) { // 다르거나 관리자가 아니면
            throw new CommonException(CommonError.FORBIDDEN);
        }

        // 경기 정보 수정
        if (!game.getId().equals(updateDto.getGameId())) {
            game = gameRepository.findById(updateDto.getGameId())
                    .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));
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
    public PartyPostDeleteResponseDto deletePartyPost(Long id, User user) {

        PartyPost deletePartyPost = partyPostRepository.findById(id)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));

        if (!deletePartyPost.getUser().getId().equals(user.getId()) || !user.getRole().equals(UserRoleEnum.ADMIN)) { // 관리자가 아니거나 동일한 유저가 아니라면
            throw new CommonException(CommonError.FORBIDDEN);
        }
        partyPostRepository.delete(deletePartyPost);
        return PartyPostDeleteResponseDto.builder()
                .id(deletePartyPost.getId())
                .build();
    }

   


    // 검색 조건에 따른 리스트 출력
    @Override
    public PartyPostPageResponseDto getPartyPosts(String teamName, Long gameId, int page, int size, String[] sortBy, boolean ascending) {

        // 페이지 번호와 크기 유효성 검사
        if (page < 0) page = 0;
        if (size < 1) size = 10;

        // 페이징 정보 생성
        Pageable pageable = PageRequest.of(page, size);

        // Specification 동적으로 결합 (필터링 조건을 동적으로 설정)
        Specification<PartyPost> spec = Specification.where(PartyPostSpecification.hasTeam(teamName))
                .and(PartyPostSpecification.hasGame(gameId));

        // 정렬 조건 추가 (최신순 작성순으로 최우선 정렬)
        spec = spec.and(ascending
                ? PartyPostSpecification.orderByCreatedAtAsc()
                : PartyPostSpecification.orderByCreatedAtDesc());

        // 동적으로 정렬 기준 추가
        for (String sort : sortBy) {
            switch (sort) {
                case "hitCount":
                    spec = spec.and(PartyPostSpecification.orderByHitCountDesc());
                    break;
                case "likeCount":
                    spec = spec.and(PartyPostSpecification.orderByLikeCountDesc());
                    break;
                default:
                    break;
            }
        }

        // Specification Pageable 사용하여 데이터 조회
        Page<PartyPost> partyPostPage = partyPostRepository.findAll(spec, pageable);

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

    // 조건에 따라 자기가 작성한 글 또는 자기가 좋아요한 글들을 가져옴
    @Override
    public List<PartyPostMyPageResponseDto> findMyPostsByType(String type, User user) {

        return  switch (type) {
            case "written" -> // 작성한 게시글 조회
                    partyPostRepository.findByUser(user).stream()
                            .map(PartyPostMyPageResponseDto::fromEntity)
                            .collect(Collectors.toList());
            case "liked" -> // 좋아요한 게시글 조회
                    likeRepository.findByUser(user).stream()
                            .map(Like::getPartyPost) // 좋아요한 PartyPost 추출
                            .map(PartyPostMyPageResponseDto::fromEntity)
                            .collect(Collectors.toList());
            default -> // 예외 처리
                    throw new CommonException(CommonError.BAD_REQUEST);
        };
    }

}

