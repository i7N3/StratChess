package cz.czu.nick.chess.backend.service;

import cz.czu.nick.chess.backend.model.Color;
import cz.czu.nick.chess.backend.model.Game;
import cz.czu.nick.chess.backend.model.Player;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Scope("singleton")
public class GameService {

    private Map<String, Game> games = new ConcurrentHashMap<>();

    public String createGame() {
        Game game = new Game();
        String sessionId = UUID.randomUUID().toString();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        game.setPlayer1(new Player(username, Color.white));
        games.put(sessionId, game);

        return sessionId;
    }

    public void joinGame(String sessionId) {
        if (sessionId == null) return;
        if (sessionId.length() == 0) return;

        Game game = games.get(sessionId);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (game == null) return;

        if (game.getPlayer1() == null) {
            game.setPlayer1(new Player(username, Color.white));
        } else {
            game.setPlayer2(new Player(username, Color.black));
        }

        if (!game.isStarted) {
            game.start();
        }

        games.put(sessionId, game);
    }

    public void setGame(String id, Game game) {
        games.put(id, game);
    }

    public void removeGame(String id) {
        games.remove(id);
    }

    public Game getGameBySessionId(String sessionId) {
        return games.get(sessionId);

    }

    public Map<String, Game> getAvailableGames() {
        return games.entrySet()
                .stream()
                .filter(map -> !map.getValue().isStarted())
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
    }
}
