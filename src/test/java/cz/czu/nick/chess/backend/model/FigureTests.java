package cz.czu.nick.chess.backend.model;

import org.junit.Assert;
import org.junit.Test;

public class FigureTests {
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
    public void getColor() {
        Color colorNone = Figure.getColor(none);
        Color colorBlack = Figure.getColor(blackPawn);
        Color colorWhite = Figure.getColor(whiteKing);

        Assert.assertEquals(Color.none, colorNone);
        Assert.assertEquals(Color.black, colorBlack);
        Assert.assertEquals(Color.white, colorWhite);
    }
}
