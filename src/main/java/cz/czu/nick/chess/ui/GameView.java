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

//    @Override
//    protected void onAttach(AttachEvent attachEvent) {
//        add(new Span("Waiting for updates"));
//
//        // Start the data feed thread
//        thread = new FeederThread(attachEvent.getUI(), this);
//        thread.start();
//    }
//
//    @Override
//    protected void onDetach(DetachEvent detachEvent) {
//        // Cleanup
//        thread.interrupt();
//        thread = null;
//    }

//    private static class FeederThread extends Thread {
//        private final UI ui;
//        private final GameView view;
//
//        private int count = 0;
//
//        public FeederThread(UI ui, GameView view) {
//            this.ui = ui;
//            this.view = view;
//        }
//
//        @Override
//        public void run() {
//            try {
//                // Update the data for a while
//                while (count < 10) {
//                    ui.access(() -> {
//                        view.add(new Span(""));
//                    });
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}

