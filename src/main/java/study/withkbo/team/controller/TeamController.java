package study.withkbo.team.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.withkbo.team.dto.response.TeamInfoResponseDto;
import study.withkbo.team.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/teamInfo")
    public ResponseEntity<List<TeamInfoResponseDto>> mainPage() {
        return ResponseEntity.ok(teamService.selectTeamInfo());
    }
}
