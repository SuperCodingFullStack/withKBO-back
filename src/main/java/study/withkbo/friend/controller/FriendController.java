package study.withkbo.friend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.withkbo.friend.dto.request.FriendRequestDto;
import study.withkbo.friend.dto.response.FriendResponseDto;
import study.withkbo.friend.service.FriendService;
import study.withkbo.user.entity.User;

import java.util.List;

@RequiredArgsConstructor
@RestController("/api/friend")
public class FriendController {

    private final FriendService friendService;

    //임시 user 객체 생성
    private User user = new User();

    @PostMapping()
    public FriendResponseDto sendFriendRequest(@RequestParam FriendRequestDto requestDto, User user){

        return friendService.sendFriendRequest(requestDto, user);
    }

    @GetMapping()
    public List<FriendResponseDto> getFriendList(User user, @RequestParam(name = "type") String type){

        return friendService.getFriendList(user, type);
    }

    @DeleteMapping()
    public FriendResponseDto blockFriend(@RequestParam FriendRequestDto requestDto,User user){

        return friendService.blockFriend(requestDto, user);
    }

    @PutMapping()
    public FriendResponseDto acceptFriendRequest(@RequestParam FriendRequestDto requestDto,User user,
                                                 @RequestParam(name = "accept") String accept){

        return friendService.acceptFriendRequest(requestDto,user,accept);
    }

}
