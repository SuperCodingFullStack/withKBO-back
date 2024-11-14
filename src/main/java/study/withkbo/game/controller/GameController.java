package study.withkbo.game.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.withkbo.game.service.GameService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GameController {

    private final GameService gameService;

    @GetMapping("/gameInfo")
    public ResponseEntity<?> gameInfo(){
        return ResponseEntity.ok(gameService.selectGameInfo());
    }
}
