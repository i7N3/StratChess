public class Moves {

    FigureMoving fm;
    Board board;

    public Moves(Board board)
    {
        this.board = board;
    }

    public boolean canMove(FigureMoving fm)
    {
        this.fm = fm;
        return canMoveFrom() && canMoveTo() && canFigureMove();
    }

    boolean canMoveFrom()
    {
        return  fm.from.onBoard() &&
                Figure.getColor(fm.figure).name() == board.moveColor.name();
    }

    boolean canMoveTo()
    {
        Figure figure = board.getFigureAt(fm.to);

        return
        (
            fm.to.onBoard() &&
            ((fm.from.x != fm.to.x) || (fm.from.y != fm.to.y)) &&
            (Figure.getColor(figure).name() != board.moveColor.name())
        );
    }

    boolean canFigureMove()
    {
        switch(fm.figure)
        {
            case whiteKing:
            case blackKing:
                // TODO: Castling
                return canKingMove();

            case whiteQueen:
            case blackQueen:
                return canStraightMove();

            case whiteRook:
            case blackRook:
                return (fm.signX() == 0 || fm.signY() == 0) &&
                        canStraightMove();

            case whiteBishop:
            case blackBishop:
                return (fm.signX() != 0 && fm.signY() != 0) &&
                        canStraightMove();

            case whiteKnight:
            case blackKnight:
                return canKnightMove();

            case whitePawn:
            case blackPawn:
                return canPawnMove();

            default: return false;
        }
    }

    private boolean canKingMove()
    {
        if (fm.absDeltaX() <= 1 && fm.absDeltaY() <= 1)
            return true;
        return false;
    }

    private boolean canKnightMove()
    {
        if (fm.absDeltaX() == 1 && fm.absDeltaY() == 2) return true;
        if (fm.absDeltaX() == 2 && fm.absDeltaY() == 1) return true;
        return false;
    }

    private boolean canStraightMove()
    {
        Square at = fm.from;

        do
        {
            at = new Square(at.x + fm.signX(), at.y + fm.signY());
            if (at.x  == fm.to.x && at.y == fm.to.y)
                return true;
        } while
        (
            at.onBoard() &&
            board.getFigureAt(at).figure == Figure.none.figure
        );
        return false;
    }

    private boolean canPawnGo(int stepY)
    {
        if (board.getFigureAt(fm.to).figure == Figure.none.figure)
            if (fm.deltaX() == 0)
                if (fm.deltaY() == stepY)
                    return true;
        return false;
    }

    private boolean canPawnJump(int stepY)
    {
        if (board.getFigureAt(fm.to).figure == Figure.none.figure)
            if (fm.deltaX() == 0)
                if (fm.deltaY() == 2 * stepY)
                    if (fm.from.y == 1 || fm.from.y == 6)
                        if (board.getFigureAt(new Square(fm.from.x, fm.from.y + stepY)).figure == Figure.none.figure)
                            return true;
        return false;
    }

    private boolean canPawnEat(int stepY)
    {
        if (board.getFigureAt(fm.to).figure != Figure.none.figure)
            if (fm.absDeltaX() == 1)
                if (fm.deltaY() == stepY)
                    return true;
        return false;
    }

    private boolean canPawnMove()
    {
        if (fm.from.y < 1 || fm.from.y > 6) return false;
        int stepY = Figure.getColor(fm.figure).name() == Color.white.name() ? 1 : -1;
        return
            canPawnGo(stepY) ||
            canPawnJump(stepY) ||
            canPawnEat(stepY);
            // TODO: En passant
    }
}