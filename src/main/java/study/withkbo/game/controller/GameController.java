package study.withkbo.game.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
import study.withkbo.game.dto.response.GameInfoResponseDto;
import study.withkbo.game.dto.response.GameResponseDto;
import study.withkbo.game.service.GameService;
import study.withkbo.security.UserDetailsImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GameController {

    private final GameService gameService;

    @GetMapping("/gameInfo/{month}")
    public ApiResponseDto<List<GameInfoResponseDto>> gameInfo(@PathVariable String month){
        List<GameInfoResponseDto> result = gameService.selectGameInfo(month);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }

    @GetMapping("/gameMatchDate")
    public ApiResponseDto<List<GameResponseDto>> gameMatchDate(@RequestParam String matchDate){
        List<GameResponseDto> result = gameService.gameInfo(matchDate);
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }
}
