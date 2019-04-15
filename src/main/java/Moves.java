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
                return CanQueenMove();

            case whiteRook:
            case blackRook:
                return CanRookMove();

            case whiteBishop:
            case blackBishop:
                return CanBishopMove();

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

    private boolean CanPawnMove()
    {
        return true;
    }
}