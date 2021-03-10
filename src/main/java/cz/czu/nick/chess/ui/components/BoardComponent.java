package cz.czu.nick.chess.ui.components;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;
import cz.czu.nick.chess.backend.model.*;
import cz.czu.nick.chess.backend.service.Broadcaster;
import cz.czu.nick.chess.backend.service.GameService;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.stream.IntStream;

// TODO: WIN / DRAW IN THE GAME
public class BoardComponent extends Div {

    private GameService gameService;
    private Registration broadcasterRegistration;
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    private Game game;
    private String sessionId;

    private Figure selectedFigure = Figure.none;
    private ChessPieceComponent selectedChessPiece = new ChessPieceComponent(Figure.none);

    private Square to = null;
    private Square from = null;

    private Div board = new Div();
    Div wrapperInnerTop = new Div();
    Div wrapperInnerBottom = new Div();
    private ChessPieceComponent[][] chessPieces = new ChessPieceComponent[8][8];

    public BoardComponent(Game game, String sessionId, GameService gameService) {
        this.game = game;
        this.sessionId = sessionId;
        this.gameService = gameService;

        board.addClassName("board");

        Div wrapper = new Div();
        wrapper.addClassName("wrapper");

        Div top = new Div();
        top.addClassName("top");

        Div bottom = new Div();
        bottom.addClassName("bottom");

        wrapperInnerTop.addClassName("wrapper-inner");
        wrapperInnerBottom.addClassName("wrapper-inner");

        init();

        top.add(wrapperInnerTop);
        wrapper.add(top);

        wrapper.add(board);
        updateBoard();

        bottom.add(wrapperInnerBottom);
        wrapper.add(bottom);

        add(wrapper);
    }

    private void init() {
        // First player is always WHITE
        boolean isFirstPlayer = username == game.player1.getUsername();

        if (isFirstPlayer) {
            IntStream.range('A', 'I').forEach(this::printTopAndBottom);
        } else {
            IntStream.range('A', 'I').map(i -> 'I' - i + 'A' - 1).forEach(this::printTopAndBottom);
        }
    }

    private void printTopAndBottom(int i) {
        Div boxInner1 = new Div();
        boxInner1.addClassName("box-inner");
        boxInner1.setText(Character.toString((char) i));

        Div boxInner2 = new Div();
        boxInner2.addClassName("box-inner");
        boxInner2.setText(Character.toString((char) i));

        wrapperInnerTop.add(boxInner1);
        wrapperInnerBottom.add(boxInner2);
    }

    private void handleCell(ClickEvent event, ChessPieceComponent piece, Figure figure, Square square) {
        if (!game.isStarted) return;
        if (game.currentPlayer.getUsername() != username) return;
        if (game.currentPlayer.getColor() != game.getMoveColor()) return;

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

        // send push event to client on move
        broadcast();
    }

    private void broadcast() {
        String username;
        if (game.getMoveColor() == game.player1.getColor()) {
            username = game.player1.getUsername();
        } else {
            username = game.player2.getUsername();
        }
        Broadcaster.broadcast(username, game.getFen());
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
            // First player is always WHITE
            boolean isFirstPlayer = username == game.player1.getUsername();

            board.getElement().removeAllChildren();

            if (isFirstPlayer) {
                for (int y = 7; y >= 0; y--) {
                    for (int x = 0; x < 8; x++) {
                        char f = game.getFigureAt(x, y);

                        Square square = new Square(x, y);
                        Figure figure = Figure.getFigureType(f);
                        ChessPieceComponent chessPiece = new ChessPieceComponent(figure, square);

                        chessPiece.addClickListener(e -> {
                            handleCell(e, chessPiece, figure, square);
                        });

                        chessPieces[x][y] = chessPiece;
                        board.add(chessPiece);
                    }
                }
            } else {
            for (int y = 0; y < 8; y++) {
                for (int x = 7; x >= 0; x--) {
                    char f = game.getFigureAt(x, y);

                    Square square = new Square(x, y);
                    Figure figure = Figure.getFigureType(f);
                    ChessPieceComponent chessPiece = new ChessPieceComponent(figure, square);

                    chessPiece.addClickListener(e -> {
                        handleCell(e, chessPiece, figure, square);
                    });

                    chessPieces[x][y] = chessPiece;
                    board.add(chessPiece);
                }
            }
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        broadcasterRegistration = Broadcaster.register(username, newFen -> {
            ui.access(() -> {
                game = new Game(new Board(newFen), game);
                gameService.setGame(sessionId, game);
                updateBoard();
            });
        });
    }

    @Override
    // TODO: handle closing tab & manual changing url
    protected void onDetach(DetachEvent detachEvent) {
        if (game.getPlayer1() == null && game.getPlayer2() == null) {
            gameService.removeGame(sessionId);
            return;
        }

        game.setStarted(false);
        if (game.getPlayer1() != null && game.getPlayer1().getUsername().equals(username)) {
            game.setPlayer1(null);
        }
        if (game.getPlayer2() != null && game.getPlayer2().getUsername().equals(username)) {
            game.setPlayer2(null);
        }

        gameService.setGame(sessionId, game);

        broadcasterRegistration.remove();
        broadcasterRegistration = null;
    }
}
