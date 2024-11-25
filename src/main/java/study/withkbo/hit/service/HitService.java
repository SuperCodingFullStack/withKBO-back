package study.withkbo.hit.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.hit.entity.Hit;
import study.withkbo.hit.repository.HitRepository;
import study.withkbo.partypost.entity.PartyPost;
import study.withkbo.partypost.repository.PartyPostRepository;
import study.withkbo.user.entity.User;

@Slf4j
@Service
@AllArgsConstructor
public class HitService {

    private  final HitRepository hitRepository;
    private final PartyPostRepository partyPostRepository;


    // 조회수 증가시키는 메소드
    @Transactional
    public void hitUp(Long postId, User user) {
        PartyPost partyPost = partyPostRepository.findById(postId)
                .orElseThrow(() -> new CommonException(CommonError.NOT_FOUND));

        // 조회 기록을 바로 조회하고 처리
        boolean alreadyHit = hitRepository.existsByUserAndPartyPost(user, partyPost);

        if (!alreadyHit) {
            hitRepository.save(new Hit(user, partyPost)); // 조회 기록 추가
            partyPost.incrementHitCount(); // 조회수 증가

        }
        // 트랜잭션이라 자동수정
    }

}
