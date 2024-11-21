package study.withkbo.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.withkbo.game.entity.Game;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("select g from Game g where g.matchDate like :matchDate%")
    List<Game> findByMatchDate(String matchDate);

    @Query("SELECT g FROM Game g WHERE g.matchDate = :matchDate AND g.team.teamName = :homeTeamName AND g.awayTeam = :awayTeamName")
    Optional<Game> findByMatchDateAndHomeTeamAndAwayTeam(
            @Param("matchDate") String matchDate,
            @Param("homeTeamName") String homeTeamName,
            @Param("awayTeamName") String awayTeamName
    );

}
