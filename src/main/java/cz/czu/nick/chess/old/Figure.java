package cz.czu.nick.chess.old;

import cz.czu.nick.chess.backend.model.Color;

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

    Figure()
    {
        this.figure = '.';
    }

    Figure(char figure)
    {
        this.figure = figure;
    }

    static Figure getFigureType(char figure)
    {
        for(Figure f : Figure.values())
            if(f.figure == figure) return f;

        return Figure.none;
    }

    static Color getColor(Figure figure)
    {
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
}