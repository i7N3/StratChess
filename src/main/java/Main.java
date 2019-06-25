import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// TODO: make package cz.czu.nick.chess.algorithms
public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Chess chess = new Chess();
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> list;

        while (true)
        {
            list = chess.getAllMoves();

            System.out.println(chess.fen);
            System.out.println(chessToAscii(chess));

            for (String moves : list)
                System.out.print(moves + " ");
            System.out.println();
            String move = scanner.nextLine();

            if (move.length() == 0) {
                move = list.get(random.nextInt(list.size()));
            }
            if (move.charAt(0) == 'q') {
                break;
            }


            chess = chess.move(move);
        }
    }

    static String chessToAscii(Chess chess)
    {
        String text = "  +-----------------+\n";
        for (int y = 7; y >= 0; y--)
        {
            text += y + 1;
            text += " | ";
            for (int x = 0; x < 8; x++)
                text += "" + chess.getFigureAt(x, y) + " ";

            text += "|\n";
        }
        text += "  +-----------------+\n";
        text += "    a b c d e f g h\n";
        return text;
    }
}