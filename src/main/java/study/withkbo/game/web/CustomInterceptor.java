package study.withkbo.game.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import study.withkbo.team.service.TeamService;

@Component
@RequiredArgsConstructor
public class CustomInterceptor implements HandlerInterceptor {

    private final TeamService teamService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        teamService.selectTeamInfo();

        return true;
    }

}
