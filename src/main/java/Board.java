import lombok.Getter;
import lombok.Setter;

public class Board {

    @Getter @Setter String fen;
    @Getter @Setter public Color moveColor;
    @Getter @Setter int moveNumber;
    Figure[][] figures;

    public Board(String fen)
    {
        this.fen = fen;
        this.figures = new Figure[8][8];
        Init();
    }

    void Init()
    {
        SetFigureAt(new Square("a1"), Figure.whiteKing);
        SetFigureAt(new Square("h8"), Figure.blackKing);

        moveColor = Color.white;
    }

    public Figure GetFigureAt(Square square)
    {
        if(square.OnBoard() && figures[square.x][square.y] instanceof Figure)
            return figures[square.x][square.y];
        return Figure.none;
    }

    void SetFigureAt(Square square, Figure figure)
    {
        if(square.OnBoard())
            figures[square.x][square.y] = figure;
    }

    public Board Move(FigureMoving fm)
    {
        Board next = new Board(fen);
        next.SetFigureAt(fm.from, Figure.none);
        next.SetFigureAt(fm.to, fm.promotion == Figure.none ? fm.figure : fm.promotion);

        // Check this
        if(moveColor == Color.black)
            next.moveNumber++;

        next.moveColor = moveColor.FlipColor(moveColor);
        return next;
    }
}
