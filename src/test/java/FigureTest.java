import org.junit.Assert;
import org.junit.Test;

public class FigureTest {
    private Figure whiteKing = Figure.getFigureType('K');
    private Figure blackPawn = Figure.getFigureType('p');
    private Figure none = Figure.getFigureType('.');

    @Test
    public void getFigureType() {
        Assert.assertEquals(whiteKing, Figure.whiteKing);
        Assert.assertEquals(blackPawn, Figure.blackPawn);
        Assert.assertEquals(none, Figure.none);
    }

    @Test
    public void GetColor() {
        Color colorNone = Figure.GetColor(none);
        Color colorBlack = Figure.GetColor(blackPawn);
        Color colorWhite = Figure.GetColor(whiteKing);

        Assert.assertEquals(colorNone, Color.none);
        Assert.assertEquals(colorBlack, Color.black);
        Assert.assertEquals(colorWhite, Color.white);
    }
}
