import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Chess chess = new Chess();
        Scanner scanner = new Scanner(System.in);

        while (true)
        {
            System.out.println(chess.fen);
            System.out.println(ChessToAscii(chess));

            for (String moves : chess.GetAllMoves())
                System.out.print(moves + "\n");
            System.out.print("\n> ");

            String move = scanner.nextLine();
            if (move == "") break;
            chess = chess.Move(move);
        }
    }

    static String ChessToAscii(Chess chess)
    {
        String text = "  +-----------------+\n";
        for (int y = 7; y >= 0; y--)
        {
            text += y + 1;
            text += " | ";
            for (int x = 0; x < 8; x++)
                text += "" + chess.GetFigureAt(x, y) + " ";

            text += "|\n";
        }
        text += "  +-----------------+\n";
        text += "    a b c d e f g h\n";
        return text;
    }
}