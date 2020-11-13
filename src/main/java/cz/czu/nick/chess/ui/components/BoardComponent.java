package cz.czu.nick.chess.ui.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Div;
import cz.czu.nick.chess.backend.model.Figure;
import cz.czu.nick.chess.backend.model.FigureMoving;
import cz.czu.nick.chess.backend.model.FigureOnSquare;
import cz.czu.nick.chess.backend.model.Square;
import cz.czu.nick.chess.backend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class BoardComponent extends Div {

    private GameService gameService;

    private Figure selectedFigure = Figure.none;
    private ChessPieceComponent selectedChessPiece = new ChessPieceComponent(Figure.none);

    private Square to = null;
    private Square from = null;

    private Div board = new Div();
    private ChessPieceComponent[][] chessPieces = new ChessPieceComponent[8][8];

    public BoardComponent() {
    }

    public BoardComponent(@Autowired GameService gameService) {

        this.gameService = gameService;
        this.board.addClassName("board");

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

        wrapper.add(this.board);
        updateBoard();

        bottom.add(wrapperInnerBottom);
        wrapper.add(bottom);

        add(wrapper);
    }

    private void setSelectedFigure(ClickEvent event, ChessPieceComponent piece, Figure figure, Square square) {
        if (figure != Figure.none && this.gameService.getMoveColor() == Figure.getColor(figure)) {
            clear();
            markActiveSquare(piece, figure, square);
            markAvailableSquares();
        } else {
            if (this.from != null) {
                move(square);
            }
        }
    }

    private void move(Square square) {
        this.to = new Square(square.x, square.y);
        this.selectedChessPiece.removeClassName("active");

        FigureOnSquare figureOnSquare = new FigureOnSquare(this.selectedFigure, this.from);
        FigureMoving fm = new FigureMoving(figureOnSquare, this.to);

        this.gameService.move(fm.toString());

        updateBoard();

        this.selectedFigure = Figure.none;
    }

    private void markActiveSquare(ChessPieceComponent piece, Figure figure, Square square) {
        this.from = new Square(square.x, square.y);
        this.selectedChessPiece.removeClassName("active");

        this.selectedFigure = figure;
        this.selectedChessPiece = piece;

        this.selectedChessPiece.addClassName("active");
    }

    private void markAvailableSquares() {
        ArrayList<String> allMoves = this.gameService.getAllMoves();

        allMoves.forEach(move -> {
            Square s1 = new Square(move.substring(1, 3));
            Square s2 = new Square(move.substring(3, 5));

            if (this.from.x == s1.x && this.from.y == s1.y) {
                this.chessPieces[s2.x][s2.y].addClassName("validMove");
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
        this.board.getElement().removeAllChildren();

        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                char f = this.gameService.getFigureAt(x, y);

                Square square = new Square(x, y);
                Figure figure = Figure.getFigureType(f);
                ChessPieceComponent chessPiece = new ChessPieceComponent(figure, square);

                chessPiece.addClickListener(e -> {
                    setSelectedFigure(e, chessPiece, figure, square);
                });

                this.chessPieces[x][y] = chessPiece;
                this.board.add(chessPiece);
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
