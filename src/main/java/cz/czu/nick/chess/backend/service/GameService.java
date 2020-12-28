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
        String id = UUID.randomUUID().toString();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        game.setPlayer1(new Player(username, Color.white));
        games.put(id, game);

        // return session id
        return id;
    }

    public String joinGame(String id) {
        // TODO: handle exp
        Game game = games.get(id);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        game.setPlayer2(new Player(username, Color.black));
        game.setStarted(true);

        games.put(id, game);

        // return session id
        return id;
    }

    public void setGame(String id, Game game) {
        games.put(id, game);
    }

    public Game getGameBySessionId(String id) {
        return games.get(id);
    }

    public Map<String, Game> getAvailableGames() {
        return games.entrySet()
                .stream()
                .filter(map -> !map.getValue().isStarted())
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
    }
}
