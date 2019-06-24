import org.junit.Assert;
import org.junit.Test;

public class MovesTest {

    private Figure whitePawn = Figure.getFigureType('P');

    private Square from = new Square(0, 1);
    private Square to = new Square(0, 2);

    private FigureOnSquare fs = new FigureOnSquare(whitePawn, from);
    private FigureMoving fm = new FigureMoving(fs, to);

    private Board board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    private Moves moves = new Moves(board);

    private boolean canMove = moves.CanMove(fm);

    @Test
    public void CanMove() {
        Assert.assertTrue("Сan we walk the white pawn on the first move from a1 to a2 must be true", canMove);
    }

    @Test
    public void CanMoveFrom() {
        boolean canMoveFrom = moves.CanMoveFrom();
        Assert.assertTrue(canMoveFrom);
    }

    @Test
    public void CanMoveTo() {
        boolean canMoveTo = moves.CanMoveTo();
        Assert.assertTrue(canMoveTo);
    }

    @Test
    public void CanFigureMove() {
        boolean сanPawnMove = moves.CanFigureMove();
        Assert.assertTrue("Can pawn move must be true", сanPawnMove);
    }
}
