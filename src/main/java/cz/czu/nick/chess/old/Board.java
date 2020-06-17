package cz.czu.nick.chess.old;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Board {

    @Getter @Setter String fen;
    @Getter @Setter public Color moveColor;
    @Getter @Setter int moveNumber;
    static Figure[][] figures;

    public Board(String fen)
    {
        this.fen = fen;
        this.figures = new Figure[8][8];
        init();
    }

    public ArrayList<FigureOnSquare> yieldFigures()
    {
        ArrayList<FigureOnSquare> yieldFigures = new ArrayList<>();

        for (Square square : Square.yieldSquares())
            if (Figure.getColor(getFigureAt(square)).name() == moveColor.name())
                yieldFigures.add(new FigureOnSquare(getFigureAt(square), square));
        return yieldFigures;
    }

    void init()
    {
        // "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
        //  0                                           1 2    3 4 5

        String[] parts = fen.split(" ");
        if (parts.length != 6) return;

        initFigures(parts[0]);
        moveColor = parts[1] == "b" ? Color.black : Color.white;
        moveNumber = Integer.parseInt(parts[5]);
    }

    void initFigures(String data)
    {
        for (int j = 8; j >= 2; j--)
            data = data.replaceAll(Integer.toString(j), (j - 1) + "1");

        String[] lines = data.split("/");

        for (int y = 7; y >= 0; y--)
            for (int x = 0; x < 8; x++)
                this.figures[x][y] = Figure.getFigureType(lines[7 - y].charAt(x));
    }

    void generateFen()
    {
        this.fen = fenFigures() + " " +
                (moveColor.name() == Color.white.name() ? "w" : "b") +
                " - - 0 " + moveNumber;
    }

    String fenFigures()
    {
        StringBuilder sb = new StringBuilder();
        for (int y = 7; y >= 0; y--)
        {
            for (int x = 0; x < 8; x++)
                sb.append(figures[x][y] == Figure.none ? '1' : figures[x][y].figure);
            if (y > 0)
                sb.append("/");
        }

        String eight = "11111111";
        String resultFen = sb.toString();

        for (int j = 8; j >= 2; j--)
            resultFen = resultFen.replaceAll(eight.substring(0, j), Integer.toString(j));

        return resultFen;
    }

    static public Figure getFigureAt(Square square)
    {
        if(square.onBoard() && figures[square.x][square.y] instanceof Figure)
            return figures[square.x][square.y];
        return Figure.none;
    }

    void setFigureAt(Square square, Figure figure)
    {
        if(square.onBoard())
            figures[square.x][square.y] = figure;
    }

    public Board move(FigureMoving fm)
    {
        Board next = new Board(fen);
        next.setFigureAt(fm.from, Figure.none);
        next.setFigureAt(fm.to, fm.promotion.figure == Figure.none.figure ? fm.figure : fm.promotion);

        if(moveColor.name() == Color.black.name())
            next.moveNumber++;

        next.moveColor = moveColor.flipColor(moveColor);
        next.generateFen();
        return next;
    }

    // TODO: IsCheck()
}
