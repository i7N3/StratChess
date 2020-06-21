package cz.czu.nick.chess.backend.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> list;

//        while (true)
//        {
//            list = game.getAllMoves();
//
//            System.out.println(game.fen);
//            System.out.println(chessToAscii(game));
//
//            for (String moves : list)
//                System.out.print(moves + " ");
//            System.out.println();
//            String move = scanner.nextLine();
//
//            if (move.length() == 0) {
//                move = list.get(random.nextInt(list.size()));
//            }
//            if (move.charAt(0) == 'q') {
//                break;
//            }
//
//
//            game = game.move(move);
//        }
    }
}
