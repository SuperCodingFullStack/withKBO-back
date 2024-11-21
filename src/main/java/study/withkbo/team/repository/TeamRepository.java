package study.withkbo.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.team.entity.Team;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findByTeamNameIn(List<String> teamNames);

}
