package study.withkbo.friend.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
import study.withkbo.friend.dto.request.FriendRequestDto;
import study.withkbo.friend.dto.response.FriendResponseDto;
import study.withkbo.friend.service.FriendService;
import study.withkbo.user.entity.User;
import study.withkbo.user.entity.UserRoleEnum;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api")
public class FriendController {

    private final FriendService friendService;



    // 친구를 신청하는 API
    @PostMapping("/friend")
    public User sendFriendRequest(@RequestBody FriendRequestDto friendRequestDto){

        // 임시 유저 엔티티
        User testUser = User.builder().name("김세중").email("haste8324@naver.com").phone("01012341234")
                .address("용인시 삼가동").isDeleted(false).nickname("뿡뿡이").password("1234").profileImg("test.jpg")
                .username("김세중").role(UserRoleEnum.USER).build();
        return friendService.sendFriendRequest(friendRequestDto,testUser);

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
