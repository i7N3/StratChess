package cz.czu.nick.chess.ui.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Div;
import cz.czu.nick.chess.backend.model.*;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class BoardComponent extends Div {

    private Game game;

    private Figure selectedFigure = Figure.none;
    private ChessPieceComponent selectedChessPiece = new ChessPieceComponent(Figure.none);

    private Square to = null;
    private Square from = null;

    private Div board = new Div();
    private ChessPieceComponent[][] chessPieces = new ChessPieceComponent[8][8];

    public BoardComponent(Game game) {

        this.game = game;

        board.addClassName("board");

        Div wrapper = new Div();
        wrapper.addClassName("wrapper");

        Div top = new Div();
        top.addClassName("top");

        Div bottom = new Div();
        bottom.addClassName("bottom");

        Div wrapperInnerTop = new Div();
        wrapperInnerTop.addClassName("wrapper-inner");

        Div wrapperInnerBottom = new Div();
        wrapperInnerBottom.addClassName("wrapper-inner");

        IntStream.range('A', 'I').forEach(i -> {
            Div boxInner = new Div();
            boxInner.addClassName("box-inner");
            boxInner.setText(Character.toString((char) i));

            wrapperInnerTop.add(boxInner);
        });

        IntStream.range('A', 'I').forEach(i -> {
            Div boxInner = new Div();
            boxInner.addClassName("box-inner");
            boxInner.setText(Character.toString((char) i));

            wrapperInnerBottom.add(boxInner);
        });

        top.add(wrapperInnerTop);
        wrapper.add(top);

        wrapper.add(board);
        updateBoard();

        bottom.add(wrapperInnerBottom);
        wrapper.add(bottom);

        add(wrapper);
    }

    private void setSelectedFigure(ClickEvent event, ChessPieceComponent piece, Figure figure, Square square) {
        if (figure != Figure.none && game.getMoveColor() == Figure.getColor(figure)) {
            clear();
            markActiveSquare(piece, figure, square);
            markAvailableSquares();
        } else {
            if (from != null) {
                move(square);
            }
        }
    }

    private void move(Square square) {
        to = new Square(square.x, square.y);
        selectedChessPiece.removeClassName("active");

        FigureOnSquare figureOnSquare = new FigureOnSquare(selectedFigure, from);
        FigureMoving fm = new FigureMoving(figureOnSquare, to);

        game = game.move(fm.toString());
        updateBoard();

        selectedFigure = Figure.none;
    }

    private void markActiveSquare(ChessPieceComponent piece, Figure figure, Square square) {
        from = new Square(square.x, square.y);
        selectedChessPiece.removeClassName("active");

        selectedFigure = figure;
        selectedChessPiece = piece;

        selectedChessPiece.addClassName("active");
    }

    private void markAvailableSquares() {
        ArrayList<String> allMoves = game.getAllMoves();

        allMoves.forEach(move -> {
            Square s1 = new Square(move.substring(1, 3));
            Square s2 = new Square(move.substring(3, 5));

            if (from.x == s1.x && from.y == s1.y) {
                chessPieces[s2.x][s2.y].addClassName("validMove");
            }
        });
    }

    private void clear() {
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                chessPieces[x][y].removeClassName("active");
                chessPieces[x][y].removeClassName("validMove");
            }
        }
    }

    private void updateBoard() {
        board.getElement().removeAllChildren();

        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                char f = game.getFigureAt(x, y);

                Square square = new Square(x, y);
                Figure figure = Figure.getFigureType(f);
                ChessPieceComponent chessPiece = new ChessPieceComponent(figure, square);

                chessPiece.addClickListener(e -> {
                    setSelectedFigure(e, chessPiece, figure, square);
                });

                chessPieces[x][y] = chessPiece;
                board.add(chessPiece);
            }
        }
    }

//    @EventListener
//    public void handleMoveEvent (MoveEvent event, @Autowired GameService gameService) {
//        Game game = event.getGame();
//        this.updateGame(game, gameService);
//        System.out.println(game.fen);
//        add(wrapper1);
//    }
}
