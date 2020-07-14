package cz.czu.nick.chess.backend.model;

import java.util.ArrayList;

public enum Figure {

    none(),

    whiteKing('K'),
    whiteQueen('Q'),
    whiteRook('R'),
    whiteBishop('B'),
    whiteKnight('N'),
    whitePawn('P'),

    blackKing('k'),
    blackQueen('q'),
    blackRook('r'),
    blackBishop('b'),
    blackKnight('n'),
    blackPawn('p');

    char figure;

    Figure() {
        this.figure = '.';
    }

    Figure(char figure) {
        this.figure = figure;
    }

    static public Figure getFigureType(char figure) {
        for (Figure f : Figure.values())
            if (f.figure == figure) return f;

        return Figure.none;
    }

    public static Color getColor(Figure figure) {
        if (figure.figure == Figure.none.figure)
            return Color.none;

        return (figure.figure == Figure.whitePawn.figure ||
                figure.figure == Figure.whiteKing.figure ||
                figure.figure == Figure.whiteRook.figure ||
                figure.figure == Figure.whiteQueen.figure ||
                figure.figure == Figure.whiteBishop.figure ||
                figure.figure == Figure.whiteKnight.figure)
                ? Color.white
                : Color.black;
    }

    public ArrayList<Figure> yieldPromotions(Square to) {
        ArrayList<Figure> promotions = new ArrayList<Figure>();

        if (Figure.getFigureType(this.figure) == Figure.whitePawn && to.y == 7) {
            promotions.add(Figure.whiteQueen);
            promotions.add(Figure.whiteRook);
            promotions.add(Figure.whiteBishop);
            promotions.add(Figure.whiteKnight);
        } else if (Figure.getFigureType(this.figure) == Figure.blackPawn && to.y == 0) {
            promotions.add(Figure.blackQueen);
            promotions.add(Figure.blackRook);
            promotions.add(Figure.blackBishop);
            promotions.add(Figure.blackKnight);
        } else {
            promotions.add(Figure.none);
        }

        return promotions;
    }
}