import org.junit.Assert;
import org.junit.Test;

public class FigureMovingTest {

    private Figure whiteRook = Figure.getFigureType('R');

    private Square from = new Square(0, 0);
    private Square to  = new Square(0, 7);

    private FigureOnSquare fs1 = new FigureOnSquare(whiteRook, from);
    private FigureMoving fm1 = new FigureMoving(fs1, to);

    private int deltaX = fm1.DeltaX();
    private int deltaY = fm1.DeltaY();

    private int signX = fm1.SignX();
    private int signY = fm1.SignY();

    @Test
    public void figureMoving_NOT_NULL() {
        Assert.assertNotNull(fm1);
    }

    @Test
    public void deltaX() {
        // 0 - 0 must be 0
        Assert.assertEquals("to.x - from.x must be 0", 0, deltaX);
    }

    @Test
    public void deltaY() {
        // 7 - 0 must be 7
        Assert.assertEquals("to.y - from.y must be 7", 7, deltaY);
    }

    @Test
    public void signX() {
        Assert.assertEquals("deltaX == 0 must return 0", 0, signX);
        Assert.assertEquals("deltaX > 1 must return 1", 1, signX + 1);
        Assert.assertEquals("deltaX < -1 must return -1", -1, signX - 1);
    }

    @Test
    public void signY() {
        Assert.assertEquals(1, signY);
        Assert.assertEquals(0, signY - 1);
        Assert.assertEquals(-1, signY - 2);
    }

    @Test
    public void ToString() {
        String text = fm1.toString();
        Assert.assertEquals("whiteRook going from a1 to a8", "Ra1a8", text);
    }
}