package cz.czu.nick.chess.backend.repository;

import cz.czu.nick.chess.backend.model.Chess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChessRepository extends JpaRepository<Chess, Long> {
}
