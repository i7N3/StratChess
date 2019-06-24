enum Color {

    none,
    white,
    black;

    Color flipColor(Color color) {
        if (color.name() == Color.black.name()) return Color.white;
        if (color.name() == Color.white.name()) return Color.black;
        return Color.none;
    }
}