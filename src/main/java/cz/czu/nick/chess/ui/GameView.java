package cz.czu.nick.chess.ui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.*;
import cz.czu.nick.chess.backend.model.Game;
import cz.czu.nick.chess.backend.service.GameService;
import cz.czu.nick.chess.ui.components.BoardComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Push
@Route(value = GameView.ROUTE)
@CssImport("./styles/shared-styles.css")
public class GameView extends Div implements HasUrlParameter<String> {
    public static final String ROUTE = "game";

    private Game game;
    private String sessionId;
    private GameService gameService;
    private BoardComponent boardComponent;

    @Autowired
    public GameView(GameService gameService) {

        addClassName("container");
        this.gameService = gameService;
    }

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String parameter) {

        Location location = event.getLocation();
        QueryParameters queryParameters = location
                .getQueryParameters();

        Map<String, List<String>> parametersMap =
                queryParameters.getParameters();

        sessionId = parametersMap.get("sessionId").get(0);
        game = gameService.getGameBySessionId(sessionId);

        boardComponent = new BoardComponent(game, sessionId, gameService);
        add(new H1("Game"));
        add(boardComponent);
    }
}

