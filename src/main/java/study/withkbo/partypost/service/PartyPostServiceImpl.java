package study.withkbo.partypost.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 파티 게시글 서비스를 구현한 클래스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PartyPostServiceImpl implements PartyPostService {

    private final PartyPostRepository partyPostRepository; // 파티 게시글 레포지토리
    private final GameRepository gameRepository;           // 게임 레포지토리
    private final LikeRepository likeRepository;           // 좋아요 레포지토리
    private final HitRepository hitRepository;             // 조회수 레포지토리

    /**
     * 게시글 ID를 통해 특정 게시글을 조회
     *
     * @param id 게시글 ID
     * @return 조회한 게시글 정보
     */
    @Transactional
    @Override
    public PostResponseDto getPartyPostById(Long id) {
        PartyPost partyPost = partyPostRepository.findById(id)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));
        log.info("getPartyPostById: {}", partyPost);
        return PostResponseDto.fromEntity(partyPost);
    }

    /**
     * 새로운 게시글 생성
     *
     * @param partyPostRequestDto 게시글 생성 요청 데이터
     * @param user                게시글 작성자
     * @return 생성된 게시글의 ID
     */
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
                .currentPeopleNum(1) // 기본 생성 시 참가자는 1명
                .likeCount(0) // 초기 좋아요 수
                .hitCount(0) // 초기 조회 수
                .postState(true) // 게시글 활성화 상태
                .build();

        partyPostRepository.save(partyPost); // 게시글 저장
        return PartyPostWriteResponseDto.builder()
                .id(partyPost.getId()) // 생성된 게시글 ID 반환
                .build();
    }

    /**
     * 게시글 수정
     *
     * @param id         수정할 게시글 ID
     * @param user       수정 요청한 사용자
     * @param updateDto  수정 요청 데이터
     * @return 수정된 게시글의 ID
     */
    @Transactional
    @Override
    public PartyPostUpdateResponseDto updatePartyPost(Long id, User user, PartyPostUpdateRequestDto updateDto) {
        PartyPost post = partyPostRepository.findById(id)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));

        // 게시글 작성자와 수정 요청자가 일치하지 않을 경우 예외 처리
        if (!post.getUser().getId().equals(user.getId())) {
            throw new CommonException(CommonError.FORBIDDEN);
        }

        // 게임 정보 수정
        Game game = post.getGame();
        if (!game.getId().equals(updateDto.getGameId())) {
            game = gameRepository.findById(updateDto.getGameId())
                    .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));
        }

        // 수정된 정보로 게시글 업데이트
        PartyPost updatedPost = post.toBuilderWithUpdates(game, updateDto);
        partyPostRepository.save(updatedPost);
        return PartyPostUpdateResponseDto.builder()
                .id(updatedPost.getId())
                .build();
    }

    /**
     * 게시글 삭제
     *
     * @param id   삭제할 게시글 ID
     * @param user 삭제 요청한 사용자
     * @return 삭제된 게시글의 ID
     */
    @Override
    @Transactional
    public PartyPostDeleteResponseDto deletePartyPost(Long id, User user) {
        PartyPost deletePartyPost = partyPostRepository.findById(id)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));

        // 게시글 작성자와 삭제 요청자가 일치하지 않을 경우 예외 처리
        if (!deletePartyPost.getUser().getId().equals(user.getId())) {
            throw new CommonException(CommonError.FORBIDDEN);
        }

        // 관련 조회수 기록 삭제
        hitRepository.deleteByPartyPostId(id);
        partyPostRepository.delete(deletePartyPost); // 게시글 삭제
        return PartyPostDeleteResponseDto.builder()
                .id(deletePartyPost.getId())
                .build();
    }

    /**
     * 커서 기반 페이징으로 게시글 목록 조회
     *
     * @param teamName  팀 이름 필터
     * @param gameId    게임 ID 필터
     * @param cursor    페이징 커서
     * @param size      페이지 크기
     * @param sortBy    정렬 기준
     * @param ascending 오름차순 여부
     * @return 게시글 목록 및 다음 커서
     */
    @Transactional
    @Override
    public PartyPostPageResponseDto getPartyPostsWithCursor(String teamName, Long gameId, Long cursor, int size, String[] sortBy, boolean ascending) {
        if (size < 1) size = 10; // 기본 페이지 크기 설정

        Sort sort = Sort.by(sortBy); // 정렬 조건 생성
        sort = ascending ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(0, size, sort);

        // 검색 조건 설정
        Specification<PartyPost> spec = PartyPostSpecification.hasGame(gameId)
                .and(PartyPostSpecification.hasTeam(teamName));

        if (cursor != null) {
            spec = spec.and(PartyPostSpecification.idAfterCursor(cursor));
        }

        // 페이징 및 검색 조건에 맞는 게시글 조회
        List<PartyPost> partyPosts = partyPostRepository.findAll(spec, pageable).getContent();

        List<PartyPostResponseDto> partyPostResponseDtos = partyPosts.stream()
                .map(PartyPostResponseDto::fromEntity)
                .collect(Collectors.toList());

        // 다음 커서 설정
        Long nextCursor = partyPosts.isEmpty() ? null : partyPosts.get(partyPosts.size() - 1).getId();

        return PartyPostPageResponseDto.fromPartyPostResponseDtoWithCursor(
                partyPostResponseDtos,
                nextCursor
        );
    }

    /**
     * 조건에 따라 자기가 작성한 글 또는 좋아요한 글 조회
     *
     * @param type 조회 타입 (written/liked)
     * @param user 사용자
     * @return 조회 결과 목록
     */
    @Override
    @Transactional
    public List<PartyPostMyPageResponseDto> findMyPostsByType(String type, User user) {
        return switch (type) {
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

    /**
     * 좋아요 누른 글들만 조회
     *
     * @param user      사용자
     * @param teamName  팀 이름 필터
     * @param gameId    게임 ID 필터
     * @param sortBy    정렬 기준
     * @param ascending 정렬 방향 (오름차순/내림차순)
     * @param cursor    커서
     * @param size      페이지 크기
     * @return 페이징된 게시글 목록
     */
    @Transactional
    @Override
    public PartyPostPageResponseDto getLikedPosts(User user, String teamName, Long gameId, String[] sortBy, boolean ascending, Long cursor, int size) {
        // 페이지 크기 유효성 검사
        if (size < 1) size = 5;  // 기본 페이지 크기 설정

        // Pageable 생성, 정렬 기준을 sortBy 파라미터로 지정
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(0, size, sort);

        // 좋아요를 누른 게시글만 조회하는 Specification 생성
        Specification<PartyPost> spec = PartyPostSpecification.hasLikedByUser(user);

        // 커서 기반 정렬 추가
        if (cursor != null) {
            spec = spec.and(PartyPostSpecification.idAfterCursor(cursor));
        }

        // 팀 이름이나 게임 ID로 추가 필터링
        if (gameId != null) {
            spec = spec.and(PartyPostSpecification.hasGame(gameId));
        }

        if (teamName != null) {
            spec = spec.and(PartyPostSpecification.hasTeam(teamName));
        }

        // 데이터 조회
        List<PartyPost> partyPosts = partyPostRepository.findAll(spec, pageable).getContent();

        // DTO 변환
        List<PartyPostResponseDto> partyPostResponseDtos = partyPosts.stream()
                .map(PartyPostResponseDto::fromEntity)
                .collect(Collectors.toList());

        // 마지막 항목의 ID를 새로운 커서로 설정
        Long nextCursor = partyPosts.isEmpty() ? null : partyPosts.get(partyPosts.size() - 1).getId();

        // DTO 반환
        return PartyPostPageResponseDto.fromPartyPostResponseDtoWithCursor(
                partyPostResponseDtos,
                nextCursor
        );
    }

    /**
     * 내가 작성한 글들만 조회
     *
     * @param user      사용자
     * @param teamName  팀 이름 필터
     * @param gameId    게임 ID 필터
     * @param sortBy    정렬 기준
     * @param ascending 정렬 방향 (오름차순/내림차순)
     * @param cursor    커서
     * @param size      페이지 크기
     * @return 페이징된 게시글 목록
     */
    @Transactional
    @Override
    public PartyPostPageResponseDto getMyPostsWithCursor(User user, String teamName, Long gameId, String[] sortBy, boolean ascending, Long cursor, int size) {
        // 페이지 크기 유효성 검사
        if (size < 1) size = 5;  // 기본 페이지 크기 설정

        // Pageable 생성, 정렬 기준을 sortBy 파라미터로 설정
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(0, size, sort);

        // 사용자가 작성한 글만 조회하는 Specification 생성
        Specification<PartyPost> spec = PartyPostSpecification.hasPostsByUser(user);

        // 커서 기반 정렬 추가
        if (cursor != null) {
            spec = spec.and(PartyPostSpecification.idAfterCursor(cursor));
        }

        // 팀 이름이나 게임 ID로 추가 필터링
        if (gameId != null) {
            spec = spec.and(PartyPostSpecification.hasGame(gameId));
        }
        if (teamName != null) {
            spec = spec.and(PartyPostSpecification.hasTeam(teamName));
        }

        // 데이터 조회
        List<PartyPost> partyPosts = partyPostRepository.findAll(spec, pageable).getContent();

        // DTO 변환
        List<PartyPostResponseDto> partyPostResponseDtos = partyPosts.stream()
                .map(PartyPostResponseDto::fromEntity)
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

