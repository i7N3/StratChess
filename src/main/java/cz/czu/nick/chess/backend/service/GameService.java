package cz.czu.nick.chess.backend.service;

import cz.czu.nick.chess.backend.model.Game;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
// Singleton
public class GameService {

    // TODO: replace with ConcurrentHashMap
    private Map<String, Game> games = new HashMap<>();

    public Game createGame() {
        Game game = new Game();
        games.put(UUID.randomUUID().toString(), game);
        return game;
    }

    public Game joinGame(String id) {
        // TODO: handle exp
        return games.get(id);
    }

//    public char getFigureAt(int x, int y) {
//        return game.getFigureAt(x, y);
//    }
//
//    public Game move(String move) {
//        this.game = game.move(move);
//        return this.game;
//    }
//
//    public Color getMoveColor() {
//        return this.game.board.moveColor;
//    }
//
//    public ArrayList<String> getAllMoves() {
//        return this.game.getAllMoves();
//
//    }
}
