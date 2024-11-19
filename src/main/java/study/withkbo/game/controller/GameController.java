package study.withkbo.game.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.withkbo.game.dto.response.GameInfoResponseDto;
import study.withkbo.game.service.GameService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GameController {

    private final GameService gameService;

    @GetMapping("/gameInfo/{month}")
    public ResponseEntity<List<GameInfoResponseDto>> gameInfo(@PathVariable String month){
        return ResponseEntity.ok(gameService.selectGameInfo(month));
    }
}
