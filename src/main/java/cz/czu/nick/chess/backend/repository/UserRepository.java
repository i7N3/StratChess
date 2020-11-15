package cz.czu.nick.chess.backend.repository;

import cz.czu.nick.chess.backend.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
// Работа с бд
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
}
