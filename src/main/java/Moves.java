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
                Figure.GetColor(fm.figure) == board.moveColor;
    }
}