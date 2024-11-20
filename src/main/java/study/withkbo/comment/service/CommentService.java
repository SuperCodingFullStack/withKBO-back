package study.withkbo.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import study.withkbo.comment.dto.request.CommentDeleteRequestDto;
import study.withkbo.comment.dto.request.CommentRequestDto;
import study.withkbo.comment.dto.response.CommentPageResponseDto;
import study.withkbo.comment.dto.response.CommentResponseDto;
import study.withkbo.comment.entity.Comment;
import study.withkbo.comment.repository.CommentRepository;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.partypost.repository.PartyPostRepository;
import study.withkbo.user.entity.User;
import study.withkbo.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private PartyPostRepository partyPostRepository;



    // 지정된 게시글의 댓글들을 한번에 몇개씩 전체출력이 아닌 부분출력을 위한 페이지네이션
    public CommentPageResponseDto getCommentsByPostId(Long postId, int page, int size) {
        // 페이지 번호와 크기 유효성 검사 컨트롤러단에서 이미 시행
        Pageable pageable = PageRequest.of(page, size); // 페이지 번호와 페이지 크기 설정
        Page<Comment> commentPage = commentRepository.findByPartyPostId(postId, pageable);

        // getContent()를 사용하여 댓글 목록 가져오기
        List<CommentResponseDto> commentResponseDtos = commentPage.getContent().stream()
                .map(CommentResponseDto::fromEntity)
                .collect(Collectors.toList());

        return new CommentPageResponseDto(
                commentResponseDtos,
                commentPage.getTotalPages(),
                commentPage.getTotalElements(),
                commentPage.getNumber(),
                commentPage.getSize());
    }

    // 댓글 생성
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        User user = userRepository.findById(commentRequestDto.getUserId())
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));
        PartyPost partyPost = partyPostRepository.findById(commentRequestDto.getPostId())
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));

        Comment comment = Comment.builder()
                .user(user)
                .partyPost(partyPost)
                .content(commentRequestDto.getContent())
                .build();

        Comment savedComment = commentRepository.save(comment);

        return CommentResponseDto.fromEntity(savedComment);
    }

    // 댓글 삭제
    public String deleteComment(CommentDeleteRequestDto deleteRequestDto) {
        // 삭제 요청한 유저를 찾기
        User user = userRepository.findById(deleteRequestDto.getUserId())
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));

        // 삭제할 댓글을 찾기
        Comment comment = commentRepository.findById(deleteRequestDto.getCommentId())
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));

        // 댓글의 작성자와 요청한 유저의 ID가 동일한지 확인
        if (!comment.getUser().getId().equals(deleteRequestDto.getUserId())) {
            // CommonException을 발생시켜 권한 오류 처리
            throw new CommonException(CommonError.FORBIDDEN);  // 작성자와 일치하지 않으면 예외 발생
        }

        // 댓글 삭제
        commentRepository.delete(comment);

        return "댓글이 삭제되었습니다.";
    }

}
