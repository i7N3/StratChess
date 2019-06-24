import org.junit.Assert;
import org.junit.Test;

public class FigureTest {
    private Figure whiteKing = Figure.getFigureType('K');
    private Figure blackPawn = Figure.getFigureType('p');
    private Figure none = Figure.getFigureType('.');

    @Test
    public void getFigureType() {
        Assert.assertEquals(Figure.whiteKing, whiteKing);
        Assert.assertEquals(Figure.blackPawn, blackPawn);
        Assert.assertEquals(Figure.none, none);
    }

    @Test
    public void GetColor() {
        Color colorNone = Figure.GetColor(none);
        Color colorBlack = Figure.GetColor(blackPawn);
        Color colorWhite = Figure.GetColor(whiteKing);

        Assert.assertEquals(Color.none, colorNone);
        Assert.assertEquals(Color.black, colorBlack);
        Assert.assertEquals(Color.white, colorWhite);
    }
}
