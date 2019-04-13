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
}