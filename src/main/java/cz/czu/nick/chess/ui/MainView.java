package cz.czu.nick.chess.ui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import cz.czu.nick.chess.backend.service.GameService;
import cz.czu.nick.chess.ui.components.BoardComponent;
import org.springframework.beans.factory.annotation.Autowired;

@Route
@CssImport("./styles/shared-styles.css")
@PWA(name = "Chess", shortName = "Chess", description = "Chess", enableInstallPrompt = false)
public class MainView extends Div {

    private BoardComponent boardComponent = new BoardComponent();

    public MainView(@Autowired GameService gameService) {
        this.boardComponent = new BoardComponent(gameService);
        add(boardComponent);
    }
}