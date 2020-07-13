package cz.czu.nick.chess.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class Game extends AbstractEntity {

    public String fen;
    public static Board board;
    public static Moves moves;
    public static ArrayList<FigureMoving> allMoves;

    @Getter
    @Setter
    public boolean isCheck;
    @Getter
    @Setter
    public boolean isCheckmate;
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
    @Getter
    @Setter
    public boolean isStalemate;

    public Game() {
        this.fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
//        this.fen = "7k/8/5K2/8/8/8/6Q1/8 w - - 0 0";
        this.board = new Board(fen);
        this.moves = new Moves(board);

        setCheckFlags();
    }

    public Game(String fen) {
        this.fen = fen;
        this.board = new Board(fen);
        this.moves = new Moves(board);
        setCheckFlags();
    }

    public Game(Board board) {
        this.board = board;
        this.fen = board.fen;
        this.moves = new Moves(board);
        setCheckFlags();

//        System.out.println("=====================>");
//        System.out.println(
//                "IS CHECK: " + this.isCheck +
//                        " IS CHECKMATE: " + this.isCheckmate +
//                        " IS STALEMATE: " + this.isStalemate);
//        System.out.println("=====================>");
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

    public Game move(String move) {
//        ArrayList<String> allMoves = getAllMoves();
//        allMoves.forEach(m -> System.out.println(m));

        FigureMoving fm = new FigureMoving(move);

        if (!moves.canMove(fm))
            return this;

        if (this.board.isCheckAfter(fm))
            return this;

        Board nextBoard = board.move(fm);
        Game nextChess = new Game(nextBoard);

        return nextChess;
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

    void findAllMoves() {
        this.allMoves = new ArrayList<>();

        for (FigureOnSquare fs : board.yieldFigures())
            for (Square to : Square.yieldSquares()) {
                for (Figure promotion : fs.figure.yieldPromotions(to)) {
                    FigureMoving fm = new FigureMoving(fs, to, promotion);

                    if (this.moves.canMove(fm))
                        if (!this.board.isCheckAfter(fm))
                            this.allMoves.add(fm);
                }
            }
    }

    public ArrayList<String> getAllMoves() {
        findAllMoves();
        ArrayList<String> list = new ArrayList<>();

        for (FigureMoving fm : this.allMoves)
            list.add(fm.toString());
        return list;
    }
}
