import lombok.Setter;
import lombok.Getter;

public class FigureMoving {

    @Getter @Setter Figure figure;
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

    public int DeltaX()
    {
        return to.x - from.x;
    }
    public int DeltaY()
    {
        return to.y - from.y;
    }

    public int AbsDeltaX() { return Math.abs(DeltaX()); }
    public int AbsDeltaY()
    {
        return Math.abs(DeltaY());
    }

    public int SignX()
    {
        if (this.DeltaX() == 0) return 0;

        if (this.DeltaX() > 0)
            return 1;
        else
            return -1;
    }
    public int SignY()
    {
        if (this.DeltaY() == 0) return 0;

        if (this.DeltaY() > 0)
            return 1;
        else
            return -1;
    }

    // Check this
    public String toString() {
        String text = figure.figure + from.GetName() + to.GetName();

        if (promotion.figure != Figure.none.figure)
            text += promotion.figure;

        return text;
    }
}