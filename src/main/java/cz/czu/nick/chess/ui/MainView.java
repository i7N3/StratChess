package cz.czu.nick.chess.ui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import cz.czu.nick.chess.backend.model.Figure;
import cz.czu.nick.chess.backend.service.GameService;
import cz.czu.nick.chess.ui.components.ChessPieceComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.IntStream;

@Route
@CssImport("./styles/shared-styles.css")
@PWA(name = "Chess",  shortName = "Chess",  description = "Chess", enableInstallPrompt = false)
public class MainView extends Div {
    public MainView(@Autowired GameService gameService) {
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

        // Top
        IntStream.range('A', 'I').forEach(i -> {
            Div boxInner = new Div();
            boxInner.addClassName("box-inner");
            boxInner.setText(Character.toString((char)i));
            wrapperInnerTop.add(boxInner);
        });

        top.add(wrapperInnerTop);
        wrapper.add(top);

        // Squares
        for (int y = 7; y >= 0; y--)
        {
            for (int x = 0; x < 8; x++) {
                char figure = gameService.getFigureAt(x, y);

                Figure f = Figure.getFigureType(figure);
                ChessPieceComponent chessPiece = new ChessPieceComponent(f);

                wrapper.add(chessPiece);
            }
        }

        // Bottom
        IntStream.range('A', 'I').forEach(i -> {
            Div boxInner = new Div();
            boxInner.addClassName("box-inner");
            boxInner.setText(Character.toString((char)i));
            wrapperInnerBottom.add(boxInner);
        });

        bottom.add(wrapperInnerBottom);
        wrapper.add(bottom);

        add(wrapper);
    }

}
