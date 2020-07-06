package cz.czu.nick.chess.ui.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import cz.czu.nick.chess.backend.model.Figure;

public class ChessPieceComponent extends Div {

    public ChessPieceComponent(Figure figure) {
        addClassName("box");

        if (figure != Figure.none) {
            Image img = getFigureImage(figure);
            add(img);
        }
    }

    private Image getFigureImage(Figure figure) {
        if (figure == Figure.whitePawn) {
            return new Image("images/figures/white-pawn.svg", "White pawn");
        }
        if (figure == Figure.blackPawn) {
            return new Image("images/figures/black-pawn.svg", "Black pawn");
        }

        // White
        if (figure == Figure.whiteBishop) {
            return new Image("images/figures/white-bishop.svg", "White bishop");
        }
        if (figure == Figure.whiteKing) {
            return new Image("images/figures/white-king.svg", "White king");
        }
        if (figure == Figure.whiteKnight) {
            return new Image("images/figures/white-knight.svg", "White knight");
        }
        if (figure == Figure.whiteQueen) {
            return new Image("images/figures/white-queen.svg", "White queen");
        }
        if (figure == Figure.whiteRook) {
            return new Image("images/figures/white-rook.svg", "White rook");
        }

        // Black
        if (figure == Figure.blackBishop) {
            return new Image("images/figures/black-bishop.svg", "Black bishop");
        }
        if (figure == Figure.blackKing) {
            return new Image("images/figures/black-king.svg", "Black king");
        }
        if (figure == Figure.blackKnight) {
            return new Image("images/figures/black-knight.svg", "Black knight");
        }
        if (figure == Figure.blackQueen) {
            return new Image("images/figures/black-queen.svg", "Black queen");
        }
        return new Image("images/figures/black-rook.svg", "Black rook");
    }
}
