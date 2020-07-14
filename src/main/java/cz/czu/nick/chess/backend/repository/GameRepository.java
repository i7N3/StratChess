package cz.czu.nick.chess.backend.repository;

import cz.czu.nick.chess.backend.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GameRepository extends JpaRepository<Game, Long> {
}
