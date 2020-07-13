package cz.czu.nick.chess.backend.model;

import org.junit.Assert;
import org.junit.Test;

public class GameTests {

    /*
     * https://www.chessprogramming.org/Perft_Results
     * */
    @Test
    public void nextMoves() {
        // Initial Position
        Game game = new Game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
//        Assert.assertEquals(1, Game.nextMoves(0, game));
//        Assert.assertEquals(20, Game.nextMoves(1, game));
        Assert.assertEquals(400, Game.nextMoves(2, game));
//        Assert.assertEquals(8902, Game.nextMoves(3, new Game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")));
//        Assert.assertEquals(197281, Game.nextMoves(4, game));

        // Position 2
//        Game game = new Game("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
//        Assert.assertEquals(2039, Game.nextMoves(2, game));

        // Position 3
//        Game game = new Game("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w KQkq - 0 1");
//        Assert.assertEquals(14, Game.nextMoves(1, game));
//        Assert.assertEquals(191, Game.nextMoves(2, game));

        // Position 6
//        Game game = new Game("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10");
//        Assert.assertEquals(1, Game.nextMoves(0, game));
//        Assert.assertEquals(46, Game.nextMoves(1, game));
//        Assert.assertEquals(2079, Game.nextMoves(2, game));
    }
}
