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

    public boolean isCheck;
    public boolean isCheckmate;
    public boolean isStarted = false;
    /*
     * TODO:
     * Ничья может быть в случае если:
     *
     * - конь + король vs. король
     * - слон + король vs. король
     * - король vs. король
     * - drawNumber > 50
     * - 3x кратное повторение ситуации/ходов -> fen1 === fen2
     *
     * */
    public boolean isStalemate;

    public Game() {
        this.fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
//        this.fen = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R" +
//                " w KQkq - 0 1";
        this.board = new Board(fen);
        this.move = new Move(board);

        setCheckFlags();
    }

    public Game(String fen) {
        this.fen = fen;
        this.board = new Board(fen);
        this.move = new Move(board);
        setCheckFlags();
    }

    public Game(Board board) {
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

    public Game move(String move) {
        if (!isValidMove(move))
            return this;

        FigureMoving fm = new FigureMoving(move);

        Board nextBoard = this.board.move(fm);
        Game nextChess = new Game(nextBoard);

        return nextChess;
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
