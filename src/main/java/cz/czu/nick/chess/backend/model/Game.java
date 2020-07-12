package cz.czu.nick.chess.backend.model;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class Game extends AbstractEntity {

    public String fen;
    public static Board board;
    public static Moves moves;
    public static ArrayList<FigureMoving> allMoves;


    public Game() {
//        this.fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        this.fen = "r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 1";
        this.board = new Board(fen);
        this.moves = new Moves(board);
    }

    public Game(String fen) {
        this.fen = fen;
        this.board = new Board(fen);
        this.moves = new Moves(board);
    }

    public Game(Board board) {
        this.board = board;
        this.fen = board.fen;
        this.moves = new Moves(board);
    }

    public Game move(String move) {
        FigureMoving fm = new FigureMoving(move);

        if (!moves.canMove(fm))
            return this;

        Board nextBoard = board.move(fm);
        Game nextChess = new Game(nextBoard);

//        System.out.println(getAllMoves());
        System.out.println(this.fen);

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

    void findAllMoves() {
        allMoves = new ArrayList<>();

        for (FigureOnSquare fs : board.yieldFigures())
            for (Square to : Square.yieldSquares()) {
                for (Figure promotion : fs.figure.yieldPromotions(to)) {
                    FigureMoving fm = new FigureMoving(fs, to, promotion);

                    if (moves.canMove(fm))
                        allMoves.add(fm);
                }
            }
    }

    public ArrayList<String> getAllMoves() {
        findAllMoves();
        ArrayList<String> list = new ArrayList<>();

        for (FigureMoving fm : allMoves)
            list.add(fm.toString());
        return list;
    }
}
