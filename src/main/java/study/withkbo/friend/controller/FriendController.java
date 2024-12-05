package study.withkbo.friend.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
import study.withkbo.friend.dto.request.FriendRequestDto;
import study.withkbo.friend.dto.response.FriendResponseDto;
import study.withkbo.friend.entity.Friend;
import study.withkbo.friend.service.FriendService;
import study.withkbo.security.UserDetailsImpl;
import study.withkbo.user.entity.User;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/friend")
    public ApiResponseDto<FriendResponseDto> sendFriendRequest(@RequestBody FriendRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
       User user = userDetails.getUser();
       FriendResponseDto friendResponseDto = friendService.sendFriendRequest(requestDto, user);
       return ApiResponseDto.success(MessageType.SEND,friendResponseDto);
    }

    @GetMapping("/friend")
    public ApiResponseDto<List<FriendResponseDto>> getFriendList(@RequestParam(name = "type") String type, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<FriendResponseDto> result = friendService.getFriendList(type, user);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    @DeleteMapping("/friend")
    public ApiResponseDto<FriendResponseDto> blockFriend(@ModelAttribute FriendRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        FriendResponseDto result = friendService.blockFriend(requestDto, user);
        return ApiResponseDto.success(MessageType.DELETE, result);
    }

    @PutMapping("/friend")
    public ApiResponseDto<FriendResponseDto> acceptFriendRequest(@ModelAttribute FriendRequestDto requestDto,@RequestParam(name="accept") String accept, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        FriendResponseDto friendResponseDto = friendService.acceptFriendRequest(requestDto,accept,user);
        return ApiResponseDto.success(MessageType.UPDATE, friendResponseDto);
    }

}
