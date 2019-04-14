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

    Figure() { this.figure = '.'; }
    Figure(char figure) {
        this.figure = figure;
    }

    static Figure getFigureType(char figure)
    {
        for(Figure f : Figure.values())
            if(f.figure == figure) return f;

        return Figure.none;
    }

    static Color GetColor(Figure figure)
    {
        if (figure == Figure.none)
            return Color.none;

        return (figure == Figure.whitePawn ||
                figure == Figure.whiteKing ||
                figure == Figure.whiteRook ||
                figure == Figure.whiteQueen ||
                figure == Figure.whiteBishop ||
                figure == Figure.whiteKnight)
            ? Color.white
            : Color.black;
    }
}