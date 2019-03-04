enum Color {

    none,
    white,
    black;

    Color FlipColor(Color color) {
        if (color == Color.black) return Color.white;
        if (color == Color.white) return Color.black;
        return Color.none;
    }
}
