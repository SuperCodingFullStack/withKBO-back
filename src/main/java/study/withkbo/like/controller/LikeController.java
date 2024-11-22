package study.withkbo.like.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import study.withkbo.common.response.ApiResponseDto;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class LikeController {

    @PostMapping("like/{postId}")
    public ApiResponseDto<Void> toggleLike(@PathVariable Long postId, @RequestHeader("Authorization") String token) {

    }

}
