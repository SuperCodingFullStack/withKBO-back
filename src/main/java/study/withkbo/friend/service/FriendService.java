package study.withkbo.friend.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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
    private final JwtUtil jwtUtil;

    public FriendResponseDto sendFriendRequest(FriendRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.getJwtFromHeader(request);
        Claims userClaims = jwtUtil.getUserInfoFromToken(token);
        String username = userClaims.getSubject();

        User requestUser = userRepository.findByUsername(username).orElseThrow(
                () -> { throw new CommonException(CommonError.USER_NOT_FOUND); }
        );

        friendRepository.findByFromUserIdAndToUserId(
                requestDto.getToUserId(),
                requestUser.getId()
        ).ifPresent(
                (exist) -> new CommonException(CommonError.FRIEND_REQUEST_ALREADY_SEND)
        );

        Friend sendRequest = friendRepository.save(
                Friend.builder().fromUserId(requestUser.getId())
                        .toUserId(requestDto.getToUserId())
                        .state(State.SEND).build()
        );

        return new FriendResponseDto(sendRequest);
    }

    //페이징 나중에
    public List<FriendResponseDto> getFriendList(User user, String type) {

        List<Friend> friendList = new ArrayList<>();

        switch (type){
            case "recieve":
                friendList = friendRepository.findAllByToUserIdAndState(user.getId(),State.SEND);
                break;
            case "send":
                friendList = friendRepository.findAllByFromUserIdAndState(user.getId(), State.SEND);
                break;
            case "friend":
                friendList = friendRepository.findAllByFromUserIdAndState(user.getId(),State.SECCESS);
                break;
        }


        if(friendList.isEmpty()){
            throw new CommonException(CommonError.FRIEND_NOT_FOUND);
        }

        return friendList.stream().map(FriendResponseDto::new).toList();

    }

    @Transactional
    public FriendResponseDto blockFriend(FriendRequestDto requestDto,User user) {
        Friend friend = checkFriend(requestDto, user);

        if(friend.getState().equals(State.BLOCK)){
            throw new CommonException(CommonError.FRIEND_ALREADY_BLOCK);
        }

        friend.updateFriend(State.BLOCK);

        return new FriendResponseDto(friend);
    }

    @Transactional
    public FriendResponseDto acceptFriendRequest(FriendRequestDto requestDto , User user, String accept) {
        Friend friend = checkFriend(requestDto, user);

        if (!friend.getState().equals(State.SEND)){
            throw new CommonException(CommonError.BAD_REQUEST);
        }

        if(accept.equals("true")){
            friend.updateFriend(State.SECCESS);
        }else {
            friend.updateFriend(State.REJECT);
        }

        return new FriendResponseDto(friend);
    }

    public Friend checkFriend(FriendRequestDto requestDto, User user){

        return friendRepository.findByFromUserIdAndToUserId(user.getId(), requestDto.getToUserId())
                .orElseThrow(()-> new CommonException(CommonError.FRIEND_NOT_FOUND));

    }
}
