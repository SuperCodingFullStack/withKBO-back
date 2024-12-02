package study.withkbo.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.like.entity.Like;
import study.withkbo.like.repository.LikeRepository;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.partypost.repository.PartyPostRepository;
import study.withkbo.user.entity.User;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PartyPostRepository partyPostRepository;

    // 어떤 유저가 어떤 게시글을 좋아요 한 것이 있는지 확인하는 메소드
    public boolean isLiked(Long postId, User user) {
        PartyPost partyPost = partyPostRepository.findById(postId)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));

        return likeRepository.findByUserAndPartyPost(user, partyPost).isPresent();
    }


    // 좋아요를 토글하는 메소드 (좋아요 추가/삭제)
    @Transactional
    public void toggleLike(Long postId, User user) {
        PartyPost partyPost = partyPostRepository.findById(postId)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));

        Like like = likeRepository.findByUserAndPartyPost(user, partyPost).orElse(null);

        if (like != null) {
            // 좋아요가 이미 존재하면 삭제하고 카운트 감소
            likeRepository.delete(like);
            partyPost.decrementLikeCount(); // LikeCount 감소
        } else {
            // 좋아요가 없다면 추가하고 카운트 증가
            likeRepository.save(new Like(user, partyPost));
            partyPost.incrementLikeCount(); // LikeCount 증가
        }

        // 트랜잭션 내에서 `partyPost` 객체가 변경되었으므로, 별도의 `save` 호출 없이도 자동으로 반영됩니다.
    }
}
