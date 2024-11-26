package study.withkbo.team.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
import study.withkbo.security.UserDetailsImpl;
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
    public ApiResponseDto<List<TeamInfoResponseDto>> mainPage() {
        List<TeamInfoResponseDto> result = teamService.selectTeamInfo();
        return ApiResponseDto.success(MessageType.RETRIEVE, result);
    }
}
