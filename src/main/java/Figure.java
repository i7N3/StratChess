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

    Figure(char figure) {
        this.figure = figure;
    }

    Figure() {
        this.figure = '.';
    }
}