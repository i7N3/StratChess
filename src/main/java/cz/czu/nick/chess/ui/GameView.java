package cz.czu.nick.chess.ui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.*;
import cz.czu.nick.chess.backend.model.Color;
import cz.czu.nick.chess.backend.model.Game;
import com.vaadin.flow.router.AfterNavigationObserver;
import cz.czu.nick.chess.backend.model.Player;
import cz.czu.nick.chess.backend.service.GameService;
import cz.czu.nick.chess.ui.components.BoardComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Push
@Route(value = GameView.ROUTE)
@CssImport("./styles/shared-styles.css")
public class GameView extends Div implements  HasUrlParameter<String>, AfterNavigationObserver {
    public static final String ROUTE = "game";

    private Game game;
    private String sessionId;
    private GameService gameService;
    private BoardComponent boardComponent;

    @Autowired
    public GameView(GameService gameService) {
        addClassName("container");
        getStyle().set("width", "560px");
        this.gameService = gameService;
    }

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String parameter) {
        Location location = event.getLocation();
        QueryParameters queryParameters = location
                .getQueryParameters();

        Map<String, List<String>> parametersMap =
                queryParameters.getParameters();

        if (queryParameters.getParameters().containsKey("sessionId") && parametersMap.containsKey("sessionId"))  {
            sessionId = parametersMap.get("sessionId").get(0);

            if (sessionId.length() > 0 && sessionId  != null) {
                game = gameService.getGameBySessionId(sessionId);
                if (game != null) {
                    boardComponent = new BoardComponent(game, sessionId, gameService);
                    add(new H1("The game"));
                    add(boardComponent);
                } else {
                    sessionId = null;
                }
            }
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        if (sessionId == null || sessionId.length() == 0) {
            this.getUI().ifPresent(ui -> ui.navigate(MainView.class));
        }
    }
}

