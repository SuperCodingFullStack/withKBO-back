package study.withkbo.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.game.entity.Game;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findByMatchDateAndTeam_TeamNameAndAwayTeam(String matchDate, String homeTeamName, String awayTeamName);

    List<Game> findByMatchDateStartingWith(String matchDate);
}
