package cz.czu.nick.chess.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Board {

    @Getter
    @Setter
    public String fen;
    @Getter
    @Setter
    public Color moveColor;
    @Getter
    @Setter
    public int moveNumber;

    private Figure[][] figures;

    @Getter
    @Setter
    public Boolean canCastleA1;
    @Getter
    @Setter
    public Boolean canCastleH1;
    @Getter
    @Setter
    public Boolean canCastleA8;
    @Getter
    @Setter
    public Boolean canCastleH8;
    @Getter
    @Setter
    // Клетка для взятия на проходе
    public Square enpassant;
    @Getter
    @Setter
    // Счетчик для правила 50 ходов
    public int drawNumber;

    public Board(String fen) {
        this.fen = fen;
        this.figures = new Figure[8][8];
        init();
    }

    public ArrayList<FigureOnSquare> yieldFigures() {
        ArrayList<FigureOnSquare> yieldFigures = new ArrayList<>();

        for (Square square : Square.yieldSquares())
            if (Figure.getColor(getFigureAt(square)).name() == moveColor.name())
                yieldFigures.add(new FigureOnSquare(getFigureAt(square), square));
        return yieldFigures;
    }

    private void init() {
        // "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
        //  0                                           1 2    3 4 5

        String[] parts = this.fen.split(" ");
        if (parts.length != 6) return;

        initFigures(parts[0]);
        initMoveColor(parts[1]);
        initCastleFlags(parts[2]);
        initEnpassant(parts[3]);
        initDrawNumber(parts[4]);
        initMoveNumber(parts[5]);
    }

    private void initFigures(String data) {
        for (int j = 8; j >= 2; j--)
            data = data.replaceAll(Integer.toString(j), (j - 1) + "1");

        String[] lines = data.split("/");

        for (int y = 7; y >= 0; y--)
            for (int x = 0; x < 8; x++)
                this.figures[x][y] = Figure.getFigureType(lines[7 - y].charAt(x));
    }

    private void initMoveColor(String v) {
        this.moveColor = v.equals("b") ? Color.black : Color.white;
    }

    private void initCastleFlags(String v) {
        this.canCastleA1 = v.contains("Q");
        this.canCastleH1 = v.contains("K");
        this.canCastleA8 = v.contains("q");
        this.canCastleH8 = v.contains("k");
    }

    private void initEnpassant(String v) {
        this.enpassant = new Square(v);
    }

    private void initDrawNumber(String v) {
        this.drawNumber = Integer.parseInt(v);
    }

    private void initMoveNumber(String v) {
        this.moveNumber = Integer.parseInt(v);
    }

    private void generateFen() {
        this.fen = fenFigures() + " " +
                fenMoveColor() + " " +
                fenCastleFlags() + " " +
                fenEnpassant() + " " +
                fenDrawNumber() + " " +
                fenMoveNumber();
    }

    private String fenMoveColor() {
        return moveColor == Color.white ? "w" : "b";
    }

    private String fenCastleFlags() {
        String flags = (canCastleA1 ? "Q" : "") +
                (canCastleH1 ? "K" : "") +
                (canCastleA8 ? "q" : "") +
                (canCastleH8 ? "k" : "");

        return flags.length() == 0 ? "-" : flags;
    }

    private String fenEnpassant() {
        return this.enpassant.getName();
    }

    private String fenDrawNumber() {
        return Integer.toString(this.drawNumber);
    }

    private String fenMoveNumber() {
        return Integer.toString(this.moveNumber);
    }

    private String fenFigures() {
        StringBuilder sb = new StringBuilder();
        for (int y = 7; y >= 0; y--) {
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

    public Figure getFigureAt(Square square) {
        if (square.onBoard() && figures[square.x][square.y] instanceof Figure)
            return figures[square.x][square.y];
        return Figure.none;
    }

    private void setFigureAt(Square square, Figure figure) {
        if (square.onBoard())
            this.figures[square.x][square.y] = figure;
    }

    public Board move(FigureMoving fm) {
        Board next = new Board(this.fen);

        next.setFigureAt(fm.from, Figure.none);
        next.setFigureAt(fm.to, fm.placedFigure());

        next.dropEnpassant(fm);
        next.updateEnpassant(fm);
        next.moveCastleRook(fm);
        next.updateCastleFlags(fm);

        if (this.moveColor == Color.black)
            next.moveNumber++;

        next.moveColor = moveColor.flipColor(moveColor);
        next.generateFen();
        return next;
    }

    private void moveCastleRook(FigureMoving fm) {
        if (fm.figure == Figure.whiteKing) {
            Square e1 = new Square("e1");
            Square g1 = new Square("g1");
            Square c1 = new Square("c1");

            if (fm.from.y == e1.y && fm.from.x == e1.x)
                if (fm.to.y == g1.y && fm.to.x == g1.x) {
                    setFigureAt(new Square("h1"), Figure.none);
                    setFigureAt(new Square("f1"), Figure.whiteRook);
                    return;
                }

            if (fm.from.y == e1.y && fm.from.x == e1.x)
                if (fm.to.y == c1.y && fm.to.x == c1.x) {
                    setFigureAt(new Square("a1"), Figure.none);
                    setFigureAt(new Square("d1"), Figure.whiteRook);
                    return;
                }
        }

        if (fm.figure == Figure.blackKing) {
            Square e8 = new Square("e8");
            Square g8 = new Square("g8");
            Square c8 = new Square("c8");

            if (fm.from.y == e8.y && fm.from.x == e8.x)
                if (fm.to.y == g8.y && fm.to.x == g8.x) {
                    setFigureAt(new Square("h8"), Figure.none);
                    setFigureAt(new Square("f8"), Figure.blackRook);
                    return;
                }

            if (fm.from.y == e8.y && fm.from.x == e8.x)
                if (fm.to.y == c8.y && fm.to.x == c8.x) {
                    setFigureAt(new Square("a8"), Figure.none);
                    setFigureAt(new Square("d8"), Figure.blackRook);
                    return;
                }
        }
    }

    private void updateCastleFlags(FigureMoving fm) {
        switch (fm.figure) {
            case whiteKing:
                canCastleA1 = false;
                canCastleH1 = false;
                return;

            case whiteRook:
                Square a1 = new Square("a1");
                Square h1 = new Square("a1");
                if (fm.from.y == a1.y && fm.from.x == a1.x)
                    canCastleA1 = false;
                if (fm.from.y == h1.y && fm.from.x == h1.x)
                    canCastleH1 = false;
                return;

            case blackKing:
                canCastleA8 = false;
                canCastleH8 = false;
                return;

            case blackRook:
                Square a8 = new Square("a8");
                Square h8 = new Square("h8");
                if (fm.from.y == a8.y && fm.from.x == a8.x)
                    canCastleA8 = false;
                if (fm.from.y == h8.y && fm.from.x == h8.x)
                    canCastleH8 = false;
                return;

            default:
                return;
        }

    }

    private void dropEnpassant(FigureMoving fm) {
        if (fm.to.y == enpassant.y && fm.to.x == enpassant.x)
            if (fm.figure == Figure.whitePawn || fm.figure == Figure.blackPawn)
                setFigureAt(new Square(fm.to.x, fm.from.y), Figure.none);
    }

    private void updateEnpassant(FigureMoving fm) {
        if (fm.figure == Figure.whitePawn) {
            if (fm.from.y == 1 && fm.to.y == 3)
                enpassant = new Square(fm.from.x, 2);
        } else if (fm.figure == Figure.blackPawn) {
            if (fm.from.y == 6 && fm.to.y == 4)
                enpassant = new Square(fm.from.x, 5);
        } else {
            enpassant = new Square(-1, -1); // Square.none
        }
    }

    public boolean isCheck() {
        return isCheckAfter(new FigureMoving());
    }

    public boolean isCheckAfter(FigureMoving fm) {
        Board after = move(fm);
        return after.canEatKing();
    }

    private boolean canEatKing() {
        Square enemyKing = findEnemyKing();
        Move move = new Move(this);

        for (FigureOnSquare fs : yieldFigures())
            if (move.canMove(new FigureMoving(fs, enemyKing)))
                return true;

        return false;
    }

    private Square findEnemyKing() {
        Figure enemyKing =
                moveColor == Color.white ?
                        Figure.blackKing :
                        Figure.whiteKing;

        for (Square s : Square.yieldSquares())
            if (getFigureAt(s) == enemyKing)
                return s;

        return new Square(-1, -1);
    }
}
