package cz.czu.nick.chess.backend.model;

public class Moves {

    FigureMoving fm;
    Board board;

    public Moves(Board board) {
        this.board = board;
    }

    public boolean canMove(FigureMoving fm) {
        this.fm = fm;
        return canMoveFrom() && canMoveTo() && canFigureMove();
    }

    boolean canMoveFrom() {
        return fm.from.onBoard() &&
                Figure.getColor(fm.figure).name() == board.moveColor.name();
    }

    boolean canMoveTo() {
        Figure figure = board.getFigureAt(fm.to);

        return fm.to.onBoard() &&
                ((fm.from.x != fm.to.x) || (fm.from.y != fm.to.y)) &&
                (Figure.getColor(figure).name() != board.moveColor.name());
    }

    boolean canFigureMove() {
        switch (fm.figure) {
            case whiteKing:
            case blackKing:
                return canKingMove() || canKingCastle();

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

            default:
                return false;
        }
    }

    private boolean canKingMove() {
        if (fm.absDeltaX() <= 1 && fm.absDeltaY() <= 1)
            return true;
        return false;
    }

    private boolean canKingCastle() {
        if (fm.figure == Figure.whiteKing) {
            Square e1 = new Square("e1");
            Square g1 = new Square("g1");
            Square c1 = new Square("c1");

            if (fm.from.y == e1.y && fm.from.x == e1.x)
                if (fm.to.y == g1.y && fm.to.x == g1.x)
                    if (board.canCastleH1)
                        if (board.getFigureAt(new Square("h1")) == Figure.whiteRook)
                            if (board.getFigureAt(new Square("f1")) == Figure.none)
                                if (board.getFigureAt(new Square("g1")) == Figure.none)
                                    if (!board.isCheck())
                                        if (!board.isCheckAfter(new FigureMoving("Ke1f1")))
                                            return true;

            if (fm.from.y == e1.y && fm.from.x == e1.x)
                if (fm.to.y == c1.y && fm.to.x == c1.x)
                    if (board.canCastleA1)
                        if (board.getFigureAt(new Square("a1")) == Figure.whiteRook)
                            if (board.getFigureAt(new Square("b1")) == Figure.none)
                                if (board.getFigureAt(new Square("c1")) == Figure.none)
                                    if (board.getFigureAt(new Square("d1")) == Figure.none)
                                        if (!board.isCheck())
                                            if (!board.isCheckAfter(new FigureMoving("ke1d1")))
                                                return true;
        }

        if (fm.figure == Figure.blackKing) {
            Square e8 = new Square("e8");
            Square g8 = new Square("g8");
            Square c8 = new Square("c8");

            if (fm.from.y == e8.y && fm.from.x == e8.x)
                if (fm.to.y == g8.y && fm.to.x == g8.x)
                    if (board.canCastleH8)
                        if (board.getFigureAt(new Square("h8")) == Figure.blackRook)
                            if (board.getFigureAt(new Square("f8")) == Figure.none)
                                if (board.getFigureAt(new Square("g8")) == Figure.none)
                                    if (!board.isCheck())
                                        if (!board.isCheckAfter(new FigureMoving("ke8f8")))
                                            return true;

            if (fm.from.y == e8.y && fm.from.x == e8.x)
                if (fm.to.y == c8.y && fm.to.x == c8.x)
                    if (board.canCastleA8)
                        if (board.getFigureAt(new Square("a8")) == Figure.blackRook)
                            if (board.getFigureAt(new Square("b8")) == Figure.none)
                                if (board.getFigureAt(new Square("c8")) == Figure.none)
                                    if (board.getFigureAt(new Square("d8")) == Figure.none)
                                        if (!board.isCheck())
                                            if (!board.isCheckAfter(new FigureMoving("Ke8d8")))
                                                return true;
        }

        return false;
    }

    private boolean canKnightMove() {
        if (fm.absDeltaX() == 1 && fm.absDeltaY() == 2) return true;
        if (fm.absDeltaX() == 2 && fm.absDeltaY() == 1) return true;
        return false;
    }

    private boolean canStraightMove() {
        Square at = fm.from;

        do {
            at = new Square(at.x + fm.signX(), at.y + fm.signY());
            if (at.x == fm.to.x && at.y == fm.to.y)
                return true;
        } while (at.onBoard() && board.getFigureAt(at).figure == Figure.none.figure);

        return false;
    }

    private boolean canPawnGo(int stepY) {
        if (board.getFigureAt(fm.to).figure == Figure.none.figure)
            if (fm.deltaX() == 0)
                if (fm.deltaY() == stepY)
                    return true;
        return false;
    }

    private boolean canPawnJump(int stepY) {
        if (board.getFigureAt(fm.to).figure == Figure.none.figure)
            if (fm.deltaX() == 0)
                if (fm.deltaY() == 2 * stepY)
                    if (fm.from.y == 1 || fm.from.y == 6)
                        if (board.getFigureAt(new Square(fm.from.x, fm.from.y + stepY)).figure == Figure.none.figure)
                            return true;
        return false;
    }

    private boolean canPawnEat(int stepY) {
        if (board.getFigureAt(fm.to).figure != Figure.none.figure)
            if (fm.absDeltaX() == 1)
                if (fm.deltaY() == stepY)
                    return true;
        return false;
    }

    private boolean canPawnEnpassant(int stepY) {
        if (fm.to.x == board.enpassant.x && fm.to.y == board.enpassant.y)
            if (board.getFigureAt(fm.to) == Figure.none)
                if (fm.deltaY() == stepY)
                    if (fm.absDeltaX() == 1)
                        if (stepY == +1 && fm.from.y == 4 ||
                                stepY == -1 && fm.from.y == 3)
                            return true;

        return false;
    }

    private boolean canPawnMove() {
        if (fm.from.y < 1 || fm.from.y > 6) return false;
        int stepY = Figure.getColor(fm.figure).name() == Color.white.name() ? 1 : -1;
        return canPawnGo(stepY) ||
                canPawnJump(stepY) ||
                canPawnEat(stepY) ||
                canPawnEnpassant(stepY);
    }
}
