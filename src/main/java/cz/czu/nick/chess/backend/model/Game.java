package cz.czu.nick.chess.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Game {

    public String fen;
    public Board board;
    public Move move;

    public Player player1;
    public Player player2;
    public Player currentPlayer;

    public boolean isCheck;
    public boolean isCheckmate;
    public boolean isStarted = false;
    /*
     * TODO:
     * A draw occur if:
     *
     * - knight + king vs. king
     * - bishop + king vs. king
     * - king vs. king
     * - drawNumber > 50
     * - 3x repetition of moves
     *
     * */
    public boolean isStalemate;

    public Game() {
        fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        board = new Board(fen);
        move = new Move(board);

        setCheckFlags();
    }

    // This constructor only for testing
    public Game(String fen) {
        this.fen = fen;
        board = new Board(fen);
        move = new Move(board);

        this.player1 = new Player("user1", Color.white);
        this.player2 = new Player("user2", Color.black);

        setCheckFlags();
    }

    public Game(Board board, Game game) {
        this.player1 = game.player1;
        this.player2 = game.player2;
        this.isCheck = game.isCheck;
        this.isStarted = game.isStarted;
        this.isCheckmate = game.isCheckmate;

        if (board.getMoveColor() == Color.white) {
            this.currentPlayer = this.player1;
        } else {
            this.currentPlayer = this.player2;
        }

        this.board = board;
        this.fen = board.fen;
        this.move = new Move(board);
        setCheckFlags();
    }

    private void setCheckFlags() {
        this.isCheck = board.isCheck();
        this.isCheckmate = false;
        this.isStalemate = false;

        ArrayList<String> allMoves = getAllMoves();
        if (allMoves.size() > 0) return;

        if (this.isCheck)
            this.isCheckmate = true;
        else
            this.isStalemate = true;
    }

    public boolean isValidMove(String move) {
        FigureMoving fm = new FigureMoving(move);

        if (!this.move.canMove(fm))
            return false;
        if (this.board.isCheckAfter(fm))
            return false;

        return true;
    }

    public void start() {
        setStarted(true);
    }

    public Game move(String move) {
        if (!isValidMove(move))
            return this;

        FigureMoving fm = new FigureMoving(move);

        Board nextBoard = board.move(fm);
        Game nextChess = new Game(nextBoard, this);
        nextChess.flipCurrentPlayer();

        return nextChess;
    }

    private void flipCurrentPlayer() {
        if (currentPlayer.getUsername() == player1.getUsername()) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }


    public Color getMoveColor() {
        return this.board.getMoveColor();
    }

    public char getFigureAt(int x, int y) {
        Square square = new Square(x, y);
        Figure figure = this.board.getFigureAt(square);

        char result = '.';

        for (Figure f : Figure.values()) {
            if (figure.figure == f.figure)
                result = figure.figure;
        }

        return result;
    }

    public char getFigureAt(String xy) {
        Square square = new Square(xy);
        Figure figure = this.board.getFigureAt(square);

        char result = '.';

        for (Figure f : Figure.values()) {
            if (figure.figure == f.figure)
                result = figure.figure;
        }

        return result;
    }

    private ArrayList<FigureMoving> findAllMoves() {
        ArrayList<FigureMoving> allMoves = new ArrayList<>();

        for (FigureOnSquare fs : this.board.yieldFigures())
            for (Square to : Square.yieldSquares()) {
                for (Figure promotion : fs.figure.yieldPromotions(to)) {
                    FigureMoving fm = new FigureMoving(fs, to, promotion);

                    if (this.move.canMove(fm))
                        if (!this.board.isCheckAfter(fm))
                            allMoves.add(fm);
                }
            }

        return allMoves;
    }

    public ArrayList<String> getAllMoves() {
        ArrayList<String> list = new ArrayList<>();

        for (FigureMoving fm : findAllMoves())
            list.add(fm.toString());

        return list;
    }

    // testing helper
    // https://www.chessprogramming.org/Perft_Results
    static int nextMoves(int step, Game game) {
        if (step == 0) return 1;
        int count = 0;

        ArrayList<String> list = game.getAllMoves();
        for (String moves : list)
            count += Game.nextMoves(step - 1, game.move(moves));

        return count;
    }
}
