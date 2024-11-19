package study.withkbo.friend.dto.response;

import study.withkbo.friend.entity.Friend;
import study.withkbo.friend.entity.State;

public class FriendResponseDto {
    private Long toUserId;

    private State state;

    public FriendResponseDto(Friend friend) {
        this.toUserId = friend.getToUserId();
        this.state = friend.getState();
    }
}
