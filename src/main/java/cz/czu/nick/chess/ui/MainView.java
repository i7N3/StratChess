package cz.czu.nick.chess.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import cz.czu.nick.chess.backend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Route
@CssImport("./styles/shared-styles.css")
@PWA(name = "Chess", shortName = "Chess", description = "Chess", enableInstallPrompt = false)
public class MainView extends Div {
    // TODO: fix double init after login

    private GameService gameService;

    private ListBox list = new ListBox();
    private Button startBtn = new Button("Start new game");
    private Button updateBtn = new Button("Update list of available games");

    @Autowired
    public MainView(GameService gameService) {
        this.gameService = gameService;

        addClassName("dashboard");
        startBtn.addClickListener(this::startNewGame);

        add(new H1("Dashboard"), startBtn, updateBtn, new H2("Available games"), list);

        updateList();
    }

    private void startNewGame(ClickEvent event) {
        String sessionId = gameService.createGame();
        navigateToGame(sessionId);
    }

    private void joinGame(ClickEvent event, String id) {
        String sessionId = gameService.joinGame(id);
        navigateToGame(sessionId);
    }

    private void navigateToGame(String sessionId) {
        List<String> list = new ArrayList<String>();
        Map<String, List<String>> parametersMap = new HashMap<String, List<String>>();

        list.add(sessionId);
        parametersMap.put("sessionId", list);

        QueryParameters queryParameters = new QueryParameters(parametersMap);

        this.getUI().ifPresent(ui -> ui.navigate(GameView.ROUTE, queryParameters));
    }


    private void updateList() {
        if (gameService.getAvailableGames().size() == 0) {
            add(new Paragraph("No games available"));
            return;
        }

        list.getElement().removeAllChildren();

        gameService.getAvailableGames().forEach((id, g) -> {
            HorizontalLayout row = new HorizontalLayout();

            Button btn = new Button("join");
            btn.addClickListener(e -> this.joinGame(e, id));

            row.add(new Paragraph(id));
            row.add(btn);

            list.add(row);
        });
    }
}