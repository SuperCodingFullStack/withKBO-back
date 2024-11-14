package study.withkbo.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.game.entity.Game;

public interface GameRepository extends JpaRepository<Game, Integer> {
}
