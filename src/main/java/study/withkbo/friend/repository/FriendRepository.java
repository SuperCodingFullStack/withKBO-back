package study.withkbo.friend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.friend.dto.response.FriendResponseDto;
import study.withkbo.friend.entity.Friend;
import study.withkbo.friend.entity.State;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {


    List<Friend> findAllByToUserIdAndState(Long id, State state);

    List<Friend> findAllByFromUserIdAndState(Long id, State state);


    Optional<Friend> findByFromUserIdAndToUserId(Long toUserId, Long id);
}
