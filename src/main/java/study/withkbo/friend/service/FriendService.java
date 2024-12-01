package study.withkbo.friend.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import study.withkbo.common.response.MessageType;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.friend.dto.request.FriendRequestDto;
import study.withkbo.friend.dto.response.FriendResponseDto;
import study.withkbo.friend.entity.Friend;
import study.withkbo.friend.entity.State;
import study.withkbo.friend.repository.FriendRepository;
import study.withkbo.jwt.JwtUtil;
import study.withkbo.user.entity.User;
import study.withkbo.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public FriendResponseDto sendFriendRequest(FriendRequestDto requestDto, User user) {

       friendRepository.findByFromUserIdAndToUserId(user.getId(), requestDto.getToUserId())
               .ifPresent(exist -> { new CommonException(CommonError.FRIEND_REQUEST_ALREADY_SEND); });

       Friend friend = friendRepository.save(Friend.builder().fromUserId(user.getId()).toUserId(requestDto.getToUserId()).state(State.SEND).build());

       return new FriendResponseDto(friend);
    }

    //페이징 나중에
    public List<FriendResponseDto> getFriendList(String type, User user) {
        List<Friend> friendList = new ArrayList<>();

        String username = user.getUsername();

        User targetUser = userRepository.findByUsername(username).orElseThrow(
                ()->{ throw new CommonException(CommonError.USER_NOT_FOUND);}
        );

        switch(type) {
            case "SEND":
                friendList = friendRepository.findAllByFromUserIdAndState(targetUser.getId(), State.SEND);
                break;
            case "SUCCESS":
                friendList = friendRepository.findAllByFromUserIdAndState(targetUser.getId(), State.SECCESS);
                break;
            case "BLOCK":
                friendList = friendRepository.findAllByFromUserIdAndState(targetUser.getId(), State.BLOCK);
                break;
            case "REJECTED":
                friendList = friendRepository.findAllByFromUserIdAndState(targetUser.getId(), State.REJECT);
        }

        if(friendList.isEmpty()) {
            throw new CommonException(CommonError.FRIEND_NOT_FOUND);
        }

        return friendList.stream().map(FriendResponseDto::new).toList();
    }

    @Transactional
    public FriendResponseDto blockFriend(FriendRequestDto requestDto, User user) {
        if (user == null) {
            throw new CommonException(CommonError.USER_NOT_FOUND);
        }

        Friend targetFriend = checkFriend(requestDto,user);

        if(targetFriend.getState().equals(State.BLOCK)) {
            throw new CommonException(CommonError.FRIEND_ALREADY_BLOCK);
        }

        targetFriend.updateFriend(State.BLOCK);

        return new FriendResponseDto(targetFriend);
    }

    @Transactional
    public FriendResponseDto acceptFriendRequest(FriendRequestDto requestDto, String accept, User user) {

        if(user == null) {
            throw new CommonException(CommonError.USER_NOT_FOUND);
        }

        Friend friend = checkFriend(requestDto,user);

        if(!friend.getState().equals(State.SEND)) {
            throw new CommonException(CommonError.BAD_REQUEST);
        }

        if(accept.equals("true")) {
            friend.updateFriend(State.SECCESS);
        } else {
            friend.updateFriend(State.REJECT);
        }

        return new FriendResponseDto(friend);
    }

    public Friend checkFriend(FriendRequestDto requestDto, User user){

        return friendRepository.findByFromUserIdAndToUserId(user.getId(), requestDto.getToUserId())
                .orElseThrow(()-> new CommonException(CommonError.FRIEND_NOT_FOUND));

    }
}
