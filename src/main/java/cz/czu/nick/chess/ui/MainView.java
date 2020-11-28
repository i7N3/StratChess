package cz.czu.nick.chess.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import cz.czu.nick.chess.backend.model.Game;
import cz.czu.nick.chess.backend.service.GameService;
import cz.czu.nick.chess.backend.service.UserService;
import cz.czu.nick.chess.ui.components.BoardComponent;
import org.springframework.beans.factory.annotation.Autowired;

@Route
@CssImport("./styles/shared-styles.css")
@PWA(name = "Chess", shortName = "Chess", description = "Chess", enableInstallPrompt = false)
// TODO: fix double init after login
public class MainView extends Div {

    Game game;

    private GameService gameService;
    private UserService userService;

    VerticalLayout list = new VerticalLayout();

    @Autowired
    public MainView(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;

        System.out.println("MainView");

        Button startBtn = new Button("Start new game");
        startBtn.addClickListener(this::startNewGame);

        add(startBtn);
        add(list);

        updateList();
    }

    private void startNewGame(ClickEvent event) {
        game = gameService.createGame();
        add(new BoardComponent(game));
        updateList();
    }


    private void updateList() {
        list.getElement().removeAllChildren();

        gameService.getAvailableGames().forEach((id, g) -> {
            System.out.println(id);

            HorizontalLayout row = new HorizontalLayout();

            Button btn = new Button("join");
            btn.addClickListener(e -> this.joinGame(e, id));

            row.add(new Paragraph(id));
            row.add(btn);

            list.add(row);
        });
    }

    private void joinGame(ClickEvent event, String id) {
        game = gameService.joinGame(id);
    }
}