package cz.czu.nick.chess.backend.model;

import lombok.Getter;
import lombok.Setter;

public class FigureMoving {

    @Getter
    @Setter
    Figure figure;
    @Getter @Setter Square from;
    @Getter @Setter Square to;

    // When Pawn reaches its eighth rank
    @Getter @Setter Figure promotion = Figure.none;

    public FigureMoving(FigureOnSquare fs, Square to)
    {
        this.figure = fs.figure;
        this.from = fs.square;
        this.to = to;
    }

    public FigureMoving(FigureOnSquare fs, Square to, Figure promotion)
    {
        this.figure = fs.figure;
        this.from = fs.square;
        this.to = to;
        this.promotion = promotion;
    }

    public FigureMoving(String move) // Pe2e4   Pe7e8Q
    {
        for (Figure f : Figure.values())
        {
            if (move.charAt(0) == f.figure)
                this.figure = f;
        }

        this.from = new Square(move.substring(1, 3)); // Pe2e4 => e2
        this.to = new Square(move.substring(3, 5));

        if (move.length() == 6) {
            for (Figure f : Figure.values())
            {
                if (move.charAt(5) == f.figure)
                    this.promotion = f;
                else
                    this.promotion = Figure.none;
            }
        }
    }

    public int deltaX()
    {
        return to.x - from.x;
    }
    public int deltaY()
    {
        return to.y - from.y;
    }

    public int absDeltaX()
    {
        return Math.abs(deltaX());
    }
    public int absDeltaY()
    {
        return Math.abs(deltaY());
    }

    public int signX()
    {
        if (this.deltaX() == 0) return 0;

        if (this.deltaX() > 0)
            return 1;
        else
            return -1;
    }

    public int signY()
    {
        if (this.deltaY() == 0) return 0;

        if (this.deltaY() > 0)
            return 1;
        else
            return -1;
    }

    public String toString() {
        String text = figure.figure + from.getName() + to.getName();

        if (promotion.figure != Figure.none.figure)
            text += promotion.figure;

        return text;
    }
}
