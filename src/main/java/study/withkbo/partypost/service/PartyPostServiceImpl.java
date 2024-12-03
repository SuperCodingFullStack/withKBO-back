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
import study.withkbo.hit.repository.HitRepository;
import study.withkbo.like.entity.Like;
import study.withkbo.like.repository.LikeRepository;
import study.withkbo.partypost.Specification.PartyPostSpecification;
import study.withkbo.partypost.dto.request.PartyPostUpdateRequestDto;
import study.withkbo.partypost.dto.request.PartyPostWriteRequestDto;
import study.withkbo.partypost.dto.response.*;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.partypost.repository.PartyPostRepository;
import study.withkbo.user.entity.User;
import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyPostServiceImpl implements PartyPostService {

    private final PartyPostRepository partyPostRepository;
    private final GameRepository gameRepository;
    private final LikeRepository likeRepository;
    private final HitRepository hitRepository;

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


    @Transactional
    @Override
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

        // 게시물 아이디로 조회
        PartyPost post = partyPostRepository.findById(id)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));

        // 작성자가 현재 로그인한 사용자와 일치하는지 확인
        if (!post.getUser().getId().equals(user.getId())) {
            throw new CommonException(CommonError.FORBIDDEN);
        }

        Game game = post.getGame();


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
    @Transactional
    public PartyPostDeleteResponseDto deletePartyPost(Long id, User user) {

        PartyPost deletePartyPost = partyPostRepository.findById(id)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));
        // 작성자가 현재 로그인한 사용자와 일치하는지 확인
        if (!deletePartyPost.getUser().getId().equals(user.getId())) {
            throw new CommonException(CommonError.FORBIDDEN);
        }

        // 먼저 Hit 테이블에서 해당 partyPostId에 관련된 데이터를 삭제
        hitRepository.deleteByPartyPostId(id);



        partyPostRepository.delete(deletePartyPost);
        return PartyPostDeleteResponseDto.builder()
                .id(deletePartyPost.getId())
                .build();
    }




    // 페이지네이션에 따른 조건 조회
    @Transactional
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
        for (String sort : sortBy) { // sortBy가 null이면 이 부분에서 NullPointerException 발생
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

    // 좋아요 누른 글들만 조회
    @Transactional
    @Override
    public PartyPostPageResponseDto getLikedPosts(User user, int page, int size) {
        // 페이지 번호와 크기 유효성 검사
        if (page < 0) page = 0;
        if (size < 1) size = 10;

        // Pageable 생성
        Pageable pageable = PageRequest.of(page, size);

        // 좋아요를 누른 게시글만 조회하는 Specification 생성
        Specification<PartyPost> spec = PartyPostSpecification.hasLikedByUser(user);

        // 데이터 조회
        Page<PartyPost> partyPostPage = partyPostRepository.findAll(spec, pageable);

        // DTO 변환
        List<PartyPostResponseDto> partyPostResponseDtos = partyPostPage.getContent().stream()
                .map(PartyPostResponseDto::fromEntity)
                .collect(Collectors.toList());

        // 페이지네이션 정보와 DTO 반환
        return PartyPostPageResponseDto.fromPartyPostResponseDto(
                partyPostResponseDtos,
                partyPostPage.getTotalPages(),
                partyPostPage.getTotalElements(),
                partyPostPage.getNumber(),
                partyPostPage.getSize()
        );
    }

    
    // 내가 작성한 글들만 조회
    @Transactional
    @Override
    public PartyPostPageResponseDto getMyPosts(User user, int page, int size) {
        // 페이지 번호와 크기 유효성 검사
        if (page < 0) page = 0;
        if (size < 1) size = 10;

        // Pageable 생성
        Pageable pageable = PageRequest.of(page, size);

        // 사용자 ID를 기반으로 필터링하는 Specification 호출
        Specification<PartyPost> spec = PartyPostSpecification.hasPostsByUser(user);

        // 데이터 조회
        Page<PartyPost> partyPostPage = partyPostRepository.findAll(spec, pageable);

        // DTO 변환
        List<PartyPostResponseDto> partyPostResponseDtos = partyPostPage.getContent().stream()
                .map(PartyPostResponseDto::fromEntity)
                .collect(Collectors.toList());

        // 페이지네이션 정보와 DTO 반환
        return PartyPostPageResponseDto.fromPartyPostResponseDto(
                partyPostResponseDtos,
                partyPostPage.getTotalPages(),
                partyPostPage.getTotalElements(),
                partyPostPage.getNumber(),
                partyPostPage.getSize()
        );
    }

    @Override
    public PartyPostPageResponseDto getPartyPostsWithCursor(
            String teamName, Long gameId, Long cursor, int size, String[] sortBy, boolean ascending) {

        // 페이지 크기 유효성 검사
        if (size < 1) size = 10;

        // Specification 동적으로 결합 (필터링 조건을 동적으로 설정)
        Specification<PartyPost> spec = Specification.where(PartyPostSpecification.hasTeam(teamName))
                .and(PartyPostSpecification.hasGame(gameId));

        // 기본 정렬 조건 추가 (최신순 또는 오래된 순)
        if (ascending) {
            spec = spec.and(PartyPostSpecification.createdAfterCursor(cursor));
        } else {
            spec = spec.and(PartyPostSpecification.createdBeforeCursor(cursor));
        }

        // 추가 정렬 조건 처리
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

        // 데이터 조회 (커서 기반, 최대 size만큼 조회)
        List<PartyPost> partyPosts = partyPostRepository.findAll(spec, PageRequest.of(0, size)).getContent();

        // 게시글 목록을 PartyPostResponseDto로 변환
        List<PartyPostResponseDto> partyPostResponseDtos = partyPosts.stream()
                .map(PartyPostResponseDto::fromEntity)  // 엔티티를 DTO로 변환
                .collect(Collectors.toList());

        // 마지막 항목의 ID를 새로운 커서로 설정
        Long nextCursor = partyPosts.isEmpty() ? null : partyPosts.get(partyPosts.size() - 1).getId();

        // DTO 반환
        return PartyPostPageResponseDto.fromPartyPostResponseDtoWithCursor(
                partyPostResponseDtos,
                nextCursor
        );
    }


}

