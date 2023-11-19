package cz.czu.nick.chess.backend.model;

import org.junit.Test;
import org.junit.Assert;

public class GameTests {

    /*
     * https://www.chessprogramming.org/Perft_Results
     * */
    @Test
    public void nextMoves() {
        // Initial Position
        Assert.assertEquals(1, Game.nextMoves(0, new Game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
        )));
        Assert.assertEquals(20, Game.nextMoves(1, new Game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
        )));
        Assert.assertEquals(400, Game.nextMoves(2, new Game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
        )));
        Assert.assertEquals(8902, Game.nextMoves(3, new Game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")));

        // Position 3
        Assert.assertEquals(14, Game.nextMoves(1, new Game("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w KQkq - 0 1")));
        Assert.assertEquals(191, Game.nextMoves(2, new Game("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w KQkq - 0 1")));
        Assert.assertEquals(2812, Game.nextMoves(3, new Game("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w KQkq - 0 1")));

        // Position 4
        // TODO: .. probably failing because not all logic implmented
        // Assert.assertEquals(9467, Game.nextMoves(3, new Game("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1")));

        // Position 6
        Assert.assertEquals(1, Game.nextMoves(0, new Game("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10")));
        Assert.assertEquals(46, Game.nextMoves(1, new Game("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10")));
        Assert.assertEquals(2079, Game.nextMoves(2, new Game("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10")));
    }
}
