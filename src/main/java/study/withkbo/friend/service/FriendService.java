package study.withkbo.friend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.withkbo.friend.dto.request.FriendRequestDto;
import study.withkbo.friend.dto.response.FriendResponseDto;
import study.withkbo.friend.entity.Friend;
import study.withkbo.friend.entity.State;
import study.withkbo.friend.repository.FriendRepository;
import study.withkbo.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;

    public FriendResponseDto sendFriendRequest(FriendRequestDto requestDto, User user) {
        friendRepository.findByFromUserIdAndToUserId(requestDto.getToUserId(), user.getId()).ifPresent(
                (exist) ->  new RuntimeException("error : already send request")
        );

        Friend sendRequest = friendRepository.save(Friend.builder()
                .fromUserId(user.getId())
                .toUserId(requestDto.getToUserId())
                .state(State.SEND).build());

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
            throw new RuntimeException("error : friend list not found");
        }

        return friendList.stream().map(FriendResponseDto::new).toList();

    }

    @Transactional
    public FriendResponseDto blockFriend(FriendRequestDto requestDto,User user) {
        Friend friend = checkFriend(requestDto, user);

        if(friend.getState().equals(State.BLOCK)){
            throw new RuntimeException("error : already blocked user");
        }

        friend.updateFriend(State.BLOCK);

        return new FriendResponseDto(friend);
    }

    @Transactional
    public FriendResponseDto acceptFriendRequest(FriendRequestDto requestDto , User user, String accept) {
        Friend friend = checkFriend(requestDto, user);

        if (!friend.getState().equals(State.SEND)){
            throw new RuntimeException("error : 암튼 에러 웅엥엥");
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
                .orElseThrow(()-> new RuntimeException("error : friend not found"));

    }
}
