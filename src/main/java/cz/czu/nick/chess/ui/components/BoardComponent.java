package cz.czu.nick.chess.ui.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Div;
import cz.czu.nick.chess.backend.model.Figure;
import cz.czu.nick.chess.backend.model.FigureMoving;
import cz.czu.nick.chess.backend.model.FigureOnSquare;
import cz.czu.nick.chess.backend.model.Square;
import cz.czu.nick.chess.backend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.IntStream;

public class BoardComponent extends Div {

    private ChessPieceComponent selectedChessPiece = new ChessPieceComponent(Figure.none);
    private Figure setSelectedFigure = Figure.none;

    private GameService gameService;

    private Div board = new Div();
    private Square from = null;
    private Square to = null;

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

        updateGame();

        top.add(wrapperInnerTop);
        wrapper.add(top);

        wrapper.add(this.board);

        bottom.add(wrapperInnerBottom);
        wrapper.add(bottom);

        add(wrapper);
    }

    private void setSelectedFigure(ClickEvent event, ChessPieceComponent piece, Figure figure, Square square) {
        if (figure != Figure.none && this.gameService.getMoveColor() == Figure.getColor(figure)) {
            this.from = new Square(square.x, square.y);
            this.selectedChessPiece.removeClassName("active");

            this.selectedChessPiece = piece;
            this.setSelectedFigure = figure;

            this.selectedChessPiece.addClassName("active");
        } else {
            if (this.from != null) {
                this.to = new Square(square.x, square.y);
                this.selectedChessPiece.removeClassName("active");
                move();
            }
        }
    }

    private void move() {
        FigureOnSquare figureOnSquare = new FigureOnSquare(this.setSelectedFigure, this.from);
        FigureMoving fm = new FigureMoving(figureOnSquare, this.to);

        this.gameService.move(fm.toString());
        updateGame();
        this.setSelectedFigure = Figure.none;
    }

    // TODO: Update only specific dom node
    private void updateGame() {
        if (this.board.getElement().getChildCount() > 0) {
            this.board.getElement().removeAllChildren();
        }

        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                char f = this.gameService.getFigureAt(x, y);

                Figure figure = Figure.getFigureType(f);
                ChessPieceComponent chessPiece = new ChessPieceComponent(figure);

                Square square = new Square(x, y);

                chessPiece.addClickListener(e -> {
                    setSelectedFigure(e, chessPiece, figure, square);
                });

                this.board.add(chessPiece);
            }
        }
    }

//    @EventHandler
//    private void sayHello() {
//        // Called from the template click handler
//        String userInput = getModel().getUserInput();
//        if (userInput == null || userInput.isEmpty()) {
//            getModel().setGreeting(EMPTY_NAME_GREETING);
//        } else {
//            getModel().setGreeting(String.format("Hello %s!", userInput));
//        }
//    }

//    @EventListener
//    public void handleMoveEvent (MoveEvent event, @Autowired GameService gameService) {
//        Game game = event.getGame();
//        this.updateGame(game, gameService);
//        System.out.println(game.fen);
//        add(wrapper1);
//    }
}
