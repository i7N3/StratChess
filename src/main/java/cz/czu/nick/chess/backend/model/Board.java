package cz.czu.nick.chess.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Board extends AbstractEntity {

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
        this.moveColor = v == "b" ? Color.black : Color.white;
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
//        this.fen = fenFigures() + " " +
//                (moveColor.name() == Color.white.name() ? "w" : "b") +
//                " - - 0 " + moveNumber;

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
                (canCastleH1 ? "k" : "") +
                (canCastleA8 ? "q" : "") +
                (canCastleH8 ? "k" : "");

        return flags.length() == 0 ? "-" : flags;
    }

    private String fenEnpassant() {
        return enpassant.getName();
    }

    private String fenDrawNumber() {
        return Integer.toString(drawNumber);
    }

    private String fenMoveNumber() {
        return Integer.toString(moveNumber);
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
            figures[square.x][square.y] = figure;
    }

    public Board move(FigureMoving fm) {
        Board next = new Board(fen);
        
        next.setFigureAt(fm.from, Figure.none);
        next.setFigureAt(fm.to, fm.promotion.figure == Figure.none.figure ? fm.figure : fm.promotion);

        if (moveColor == Color.black)
            next.moveNumber++;

        next.moveColor = moveColor.flipColor(moveColor);
        next.generateFen();
        return next;
    }
}
