package study.withkbo.friend.controller;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.*;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
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
    public ApiResponseDto<FriendResponseDto> sendFriendRequest(@RequestParam FriendRequestDto requestDto, User user){
        FriendResponseDto result = friendService.sendFriendRequest(requestDto, user);
        return ApiResponseDto.success(MessageType.SEND, result);
    }

    @GetMapping()
    public ApiResponseDto<List<FriendResponseDto>> getFriendList(User user, @RequestParam(name = "type") String type){
        List<FriendResponseDto> result = friendService.getFriendList(user, type);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    @DeleteMapping()
    public ApiResponseDto<FriendResponseDto> blockFriend(@RequestParam FriendRequestDto requestDto,User user){

        FriendResponseDto result = friendService.blockFriend(requestDto, user);
        return ApiResponseDto.success(MessageType.DELETE, result);
    }

    @PutMapping()
    public ApiResponseDto<FriendResponseDto> acceptFriendRequest(@RequestParam FriendRequestDto requestDto,User user,
                                                 @RequestParam(name = "accept") String accept){

        FriendResponseDto result = friendService.acceptFriendRequest(requestDto,user,accept);
        return ApiResponseDto.success(MessageType.UPDATE, result);
    }

}
