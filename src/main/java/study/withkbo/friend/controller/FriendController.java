package study.withkbo.friend.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.*;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
import study.withkbo.friend.dto.request.FriendRequestDto;
import study.withkbo.friend.dto.response.FriendResponseDto;
import study.withkbo.friend.entity.Friend;
import study.withkbo.friend.service.FriendService;
import study.withkbo.user.entity.User;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/friend")
    public ApiResponseDto<FriendResponseDto> sendFriendRequest(@RequestBody FriendRequestDto requestDto, HttpServletRequest request){
       FriendResponseDto friendResponseDto = friendService.sendFriendRequest(requestDto,request);
       return ApiResponseDto.success(MessageType.SEND,friendResponseDto);
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
