package study.withkbo.hit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
import study.withkbo.hit.service.HitService;
import study.withkbo.security.UserDetailsImpl;
import study.withkbo.user.entity.User;

@RestController
@RequestMapping("/api/hit")
@RequiredArgsConstructor
@Slf4j
public class HitController {

    private final HitService hitService;

    @PostMapping("/{postId}")
    public ApiResponseDto<Void> hitUp(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // 토큰에서 유저만 뜯어서 가져오기
        User user =userDetails.getUser();
        hitService.hitUp(postId, user);

    return ApiResponseDto.success(MessageType.SEND); // 조회수 처리 완료
        }

}
