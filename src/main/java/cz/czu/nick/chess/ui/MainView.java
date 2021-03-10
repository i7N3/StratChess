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
import cz.czu.nick.chess.backend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route
@CssImport("./styles/shared-styles.css")
public class MainView extends Div {
    GameService gameService;

    ListBox list = new ListBox();
    Paragraph text = new Paragraph("");
    Button startBtn = new Button("Spustit novou hru");
    Button updateBtn = new Button("Aktualizovat seznam dostupných her");
    Button logoutBtn = new Button("Odhlásit se");

    @Autowired
    public MainView(GameService gameService) {
        this.gameService = gameService;

        addClassName("dashboard");
        Div wrapper = new Div();
        wrapper.addClassName("dashboard__nav");
        H1 title = new H1("Domovská stránka");

        startBtn.addClickListener(this::startNewGame);
        updateBtn.addClickListener(e -> {
            updateList();
        });
        logoutBtn.addClickListener(e -> {
            this.getUI().ifPresent(ui -> ui.getPage().executeJs("window.location.href='/logout'"));
        });
        wrapper.add(startBtn, updateBtn, logoutBtn);

        add(title, wrapper, new H2("Dostupné hry"), text, list);

        updateList();
    }

    private void startNewGame(ClickEvent event) {
        String sessionId = gameService.createGame();
        navigateToGame(sessionId);
    }

    private void joinGame(ClickEvent event, String id) {
        gameService.joinGame(id);
        navigateToGame(id);
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
        text.setText("");
        list.getElement().removeAllChildren();

        if (gameService.getAvailableGames().size() == 0) {
            text.setText("Nejsou k dispozici žádné hry");
            return;
        }

        gameService.getAvailableGames().forEach((id, g) -> {
            HorizontalLayout row = new HorizontalLayout();

            Button btn = new Button("Připojit se");
            btn.addClickListener(e -> this.joinGame(e, id));

            row.add(new Paragraph(id));
            row.add(btn);

            list.add(row);
        });
    }
}