package cz.czu.nick.chess.old;

import cz.czu.nick.chess.old.*;
import org.junit.Assert;
import org.junit.Test;

public class MovesTests {

    private Figure whitePawn = Figure.getFigureType('P');

    private Square from = new Square(0, 1);
    private Square to = new Square(0, 2);

    private FigureOnSquare fs = new FigureOnSquare(whitePawn, from);
    private FigureMoving fm = new FigureMoving(fs, to);

    private Board board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    private Moves moves = new Moves(board);

    private boolean canMove = moves.canMove(fm);

    @Test
    public void canMove() {
        Assert.assertTrue("Сan we walk the white pawn on the first move from a1 to a2 must be true", canMove);
    }

    @Test
    public void canMoveFrom() {
        boolean canMoveFrom = moves.canMoveFrom();
        Assert.assertTrue(canMoveFrom);
    }

    @Test
    public void canMoveTo() {
        boolean canMoveTo = moves.canMoveTo();
        Assert.assertTrue(canMoveTo);
    }

    @Test
    public void canPawnGo() {
        boolean canPawnGo = moves.canFigureMove();
        Assert.assertTrue("canPawnGo must be true", canPawnGo);
    }

    @Test
    public void canPawnJump() {
        FigureOnSquare fs = new FigureOnSquare(whitePawn, from);
        FigureMoving fm = new FigureMoving(fs, new Square(0, 3));

        boolean сanPawnJump = moves.canMove(fm);
        Assert.assertTrue("canPawnJump must be true", сanPawnJump);
    }

    @Test
    public void canPawnEat() {
        Board board = new Board("rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1");
        Moves moves = new Moves(board);

        FigureOnSquare fs = new FigureOnSquare(whitePawn, new Square(4, 3));
        FigureMoving fm = new FigureMoving(fs, new Square(3, 4));

        boolean canWhitePawnEatBlackFigure = moves.canMove(fm);
        Assert.assertTrue(canWhitePawnEatBlackFigure);
    }

    @Test
    public void canStraightMove() {
        Board board = new Board("3qk3/ppp1pppp/8/8/8/8/PPP1PPPP/3QK3 w KQkq - 0 1");
        Moves moves = new Moves(board);

        Figure whiteQueen = Figure.getFigureType('Q');
        FigureOnSquare fs = new FigureOnSquare(whiteQueen, new Square(3, 0));
        FigureMoving fm = new FigureMoving(fs, new Square(3, 7));

        boolean canQueenStraightMove = moves.canMove(fm);
        Assert.assertTrue(canQueenStraightMove);
    }

    // TODO: all moves methods
}
