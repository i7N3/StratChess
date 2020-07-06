package cz.czu.nick.chess.backend.service;

import cz.czu.nick.chess.backend.model.Color;
import cz.czu.nick.chess.backend.model.Game;
import cz.czu.nick.chess.backend.repository.GameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private Game game = new Game();
    private GameRepository gameRepository;

    public GameService() {
    }

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public char getFigureAt(int x, int y) {
        return game.getFigureAt(x, y);
    }

    public Game move(String move) {
        this.game = game.move(move);
        return this.game;
    }

    public Color getMoveColor() {
        return this.game.board.moveColor;
    }

//    @PostConstruct
//    public void populateTestData() {
//        if (gameRepository.count() == 0) {
//            Game game = new Game();
//            gameRepository.save(game);
//        }
//    }
}