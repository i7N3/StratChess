package cz.czu.nick.chess.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import cz.czu.nick.chess.backend.model.User;
import cz.czu.nick.chess.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = RegistrationView.ROUTE)
@CssImport("./styles/shared-styles.css")
public class RegistrationView extends FormLayout implements BeforeEnterObserver {

    public static final String ROUTE = "registration";

    private UserService userService;
    private final Binder<User> binder = new Binder<>();

    private TextField username = new TextField("login");
    private TextField password = new TextField("password");

    @Autowired
    public RegistrationView(UserService userService) {
        this.userService = userService;

        add(new H1("Registration"));
        add(username);
        add(password);

        binder.forField(username).asRequired("User may not be empty")
                .bind(User::getUsername, User::setUsername);
        binder.forField(password).asRequired("User may not be empty")
                .bind(User::getPasswordHash, User::setPasswordHash);

        add(createButton());
    }

    private Button createButton() {
        Button button = new Button("Sign Up", event -> auth());
        return button;
    }

    private void auth() {
        User user = new User();
        if (binder.writeBeanIfValid(user)) {
            userService.saveUser(user);
            UI.getCurrent().navigate(LoginView.class);
        } else {
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
    }
}