package study.withkbo.like.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;

import study.withkbo.security.UserDetailsImpl;
import study.withkbo.user.entity.User;
import study.withkbo.like.service.LikeService;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
@Slf4j
public class LikeController {


    private final LikeService likeService;

    @PatchMapping("/{postId}")
    public ApiResponseDto<Void> toggleLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 토큰에서 유저만 뜯어서 가져오기
        User user =userDetails.getUser();
        likeService.toggleLike(postId, user);


        return ApiResponseDto.success(MessageType.SEND); // 좋아요 처리 완료
    }



}
