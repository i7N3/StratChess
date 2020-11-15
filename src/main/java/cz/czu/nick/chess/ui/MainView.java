package cz.czu.nick.chess.ui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import cz.czu.nick.chess.backend.model.Game;
import cz.czu.nick.chess.backend.service.GameService;
import cz.czu.nick.chess.ui.components.BoardComponent;
import org.springframework.beans.factory.annotation.Autowired;

@Route
@CssImport("./styles/shared-styles.css")
@PWA(name = "Chess", shortName = "Chess", description = "Chess", enableInstallPrompt = false)
public class MainView extends Div {

    @Autowired
    private GameService gameService;

    public MainView() {
        boolean newGame = true;
        String gameId = "123";
        Game game;
        if (newGame) {
            game = gameService.createGame();
        } else {
            game = gameService.joinGame(gameId);
        }

        add(new BoardComponent(game));
    }
}