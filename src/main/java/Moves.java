public class Moves {

    FigureMoving fm;
    Board board;

    public Moves(Board board)
    {
        this.board = board;
    }

    public boolean CanMove(FigureMoving fm)
    {
        this.fm = fm;
        return CanMoveFrom() && CanMoveTo() && CanFigureMove();
    }

    boolean CanMoveFrom()
    {
        return  fm.from.OnBoard() &&
                Figure.GetColor(fm.figure).name() == board.moveColor.name();
    }

    boolean CanMoveTo()
    {
        Figure figure = board.GetFigureAt(fm.to);

        return  fm.to.OnBoard() &&
                fm.from.x != fm.to.x || fm.from.y != fm.to.y &&
                figure.GetColor(figure).name() != board.moveColor.name();
    }

    boolean CanFigureMove()
    {
        switch (fm.figure)
        {
            case whiteKing:
            case blackKing:
                return CanKingMove();

            case whiteQueen:
            case blackQueen:
                return CanStraightMove();

            case whiteRook:
            case blackRook:
                return (fm.SignX() == 0 || fm.SignY() == 0) &&
                        CanStraightMove();

            case whiteBishop:
            case blackBishop:
                return (fm.SignX() != 0 && fm.SignY() != 0) &&
                        CanStraightMove();

            case whiteKnight:
            case blackKnight:
                return CanKnightMove();

            case whitePawn:
            case blackPawn:
                return CanPawnMove();

            default: return false;
        }
    }

    private boolean CanKingMove()
    {
        if (fm.AbsDeltaX() <= 1 && fm.AbsDeltaY() <= 1)
            return true;
        return false;
    }

    private boolean CanKnightMove()
    {
        if (fm.AbsDeltaX() == 1 && fm.AbsDeltaY() == 2) return true;
        if (fm.AbsDeltaX() == 2 && fm.AbsDeltaY() == 1) return true;
        return false;
    }

    private boolean CanStraightMove()
    {
        Square at = fm.from;

        do
        {
            at = new Square(at.x + fm.SignX(), at.y + fm.SignY());
            if ((at.x  == fm.to.x) && (at.y == fm.to.y)) return true;
        } while
        (
            at.OnBoard() &&
            board.GetFigureAt(at).figure == Figure.none.figure
        );
        return false;
    }

    private boolean CanBishopMove()
    {
        return true;
    }

    private boolean CanRookMove()
    {
        return true;
    }

    private boolean CanQueenMove()
    {
        return true;
    }

    private boolean CanPawnGo(int stepY)
    {
        if (board.GetFigureAt(fm.to).figure == Figure.none.figure)
            if (fm.DeltaX() == 0)
                if  (fm.DeltaY() == stepY)
                    return true;
        return false;
    }

    private boolean CanPawnJump(int stepY)
    {
        if (board.GetFigureAt(fm.to).figure == Figure.none.figure)
            if (fm.DeltaX() == 0)
                if (fm.DeltaY() == 2 * stepY)
                    if (fm.from.y == 1 || fm.from.y == 6)
                        if (board.GetFigureAt(new Square(fm.from.x, fm.from.y + stepY)).figure == Figure.none.figure)
                            return true;
        return false;
    }

    private boolean CanPawnEat(int stepY)
    {
        if (board.GetFigureAt(fm.to).figure != Figure.none.figure)
            if (fm.AbsDeltaX() == 1)
                if (fm.DeltaY() == stepY)
                    return true;
        return false;
    }

    private boolean CanPawnMove()
    {
        if (fm.from.y < 1 || fm.from.y > 6) return false;
        int stepY = Figure.GetColor(fm.figure).name() == Color.white.name() ? 1 : -1;
        return
            CanPawnGo(stepY) &&
            CanPawnJump(stepY) &&
            CanPawnEat(stepY);
    }
}