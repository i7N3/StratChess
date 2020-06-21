package cz.czu.nick.chess.backend.service;

import cz.czu.nick.chess.backend.model.Board;
import cz.czu.nick.chess.backend.model.Game;
import cz.czu.nick.chess.backend.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class GameService {

    private Game game = new Game();
    private String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private Board board = new Board(fen);

    private static final Logger LOGGER = Logger.getLogger(GameService.class.getName());
    private GameRepository gameRepository;

    public GameService() {}

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public char getFigureAt(int x, int y) {
        return game.getFigureAt(x, y);
    }

//    String text = "  +-----------------+\n";
//        for (int y = 7; y >= 0; y--)
//    {
//        text += y + 1;
//        text += " | ";
//        for (int x = 0; x < 8; x++)
//            text += "" + chess.getFigureAt(x, y) + " ";
//
//        text += "|\n";
//    }
//    text += "  +-----------------+\n";
//    text += "    a b c d e f g h\n";
//    return text;


//    @PostConstruct
//    public void populateTestData() {
//        if (gameRepository.count() == 0) {
//            Game game = new Game();
//            gameRepository.save(game);
//        }
//    }
}
