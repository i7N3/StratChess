package cz.czu.nick.chess.old;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Chess {

    @Getter @Setter public String fen;
    Board board;
    Moves moves;
    ArrayList<FigureMoving> allMoves;

    public Chess()
    {
        this.fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        board = new Board(fen);
        moves = new Moves(board);
    }

    public Chess(String fen)
    {
        this.fen = fen;
        board = new Board(fen);
        moves = new Moves(board);
    }

    public Chess(Board board)
    {
        this.board = board;
        this.fen = board.fen;
        moves = new Moves(board);
    }

    public Chess move(String move)
    {
        FigureMoving fm = new FigureMoving(move);
        
        if (!moves.canMove(fm))
            return this;

        Board nextBoard = board.move(fm);
        Chess nextChess = new Chess(nextBoard);
        return nextChess;
    }

    public char getFigureAt(int x, int y)
    {
        Square square = new Square(x, y);
        Figure figure = board.getFigureAt(square);

        char result = '.';

        for (Figure f : Figure.values())
        {
            if(figure.figure == f.figure)
                result =  figure.figure;
        }

        return result;
    }

    void findAllMoves()
    {
        allMoves = new ArrayList<>();

        for (FigureOnSquare fs : board.yieldFigures())
            for (Square to : Square.yieldSquares())
            {
                FigureMoving fm = new FigureMoving(fs, to);
                if (moves.canMove(fm)) allMoves.add(fm);
            }
    }

    public ArrayList<String> getAllMoves()
    {
        findAllMoves();
        ArrayList<String> list = new ArrayList<>();

        for (FigureMoving fm : allMoves)
            list.add(fm.toString());
        return list;
    }
}