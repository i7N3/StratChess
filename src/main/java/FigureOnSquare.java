import lombok.Getter;
import lombok.Setter;

public class FigureOnSquare {

    @Getter @Setter public Figure figure;
    @Getter @Setter public Square square;

    public FigureOnSquare (Figure figure, Square square)
    {
        this.figure = figure;
        this.square = square;
    }
}