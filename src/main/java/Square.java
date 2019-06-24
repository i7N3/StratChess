import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Square {
    @Getter @Setter public int x;
    @Getter @Setter public int y;

    public Square(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Square(String e2)
    {
        if (e2.length() == 2 &&
            e2.charAt(0) >= 'a' && e2.charAt(0) <= 'h' &&
            e2.charAt(1) >= '1' && e2.charAt(1) <= '8')
        {
            x = e2.charAt(0) - 'a';
            y = e2.charAt(1) - '1';
        }
        else
        {
            // return NONE
            x = -1;
            y = -1;
        }
    }

    static public ArrayList<Square> yieldSquares()
    {
        ArrayList<Square> yieldSquares = new ArrayList<>();
        for (int y = 0; y < 8; y++)
            for (int x = 0; x < 8; x++)
                yieldSquares.add(new Square(x, y));
        return yieldSquares;
    }

    public boolean onBoard()
    {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public String getName()
    {
        return ((char)('a' + x)) + Integer.toString((y + 1));
    }
}
