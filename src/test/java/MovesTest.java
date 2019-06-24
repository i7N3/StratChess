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
    public void canMove() {
        Assert.assertTrue("Сan we walk the white pawn on the first move from a1 to a2 must be true", canMove);
    }

    @Test
    public void canMoveFrom() {
        boolean canMoveFrom = moves.CanMoveFrom();
        Assert.assertTrue(canMoveFrom);
    }

    @Test
    public void canMoveTo() {
        boolean canMoveTo = moves.CanMoveTo();
        Assert.assertTrue(canMoveTo);
    }

    @Test
    public void canPawnGo() {
        boolean canPawnGo = moves.CanFigureMove();
        Assert.assertTrue("canPawnGo must be true", canPawnGo);
    }

    @Test
    public void canPawnJump() {
        FigureOnSquare fs = new FigureOnSquare(whitePawn, from);
        FigureMoving fm = new FigureMoving(fs, new Square(0, 3));

        boolean сanPawnJump = moves.CanMove(fm);
        Assert.assertTrue("canPawnJump must be true", сanPawnJump);
    }

    // TODO: all moves methods

//    Board board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
//    Moves moves = new Moves(board);
//    boolean canMove = moves.CanMove(fm);
}
