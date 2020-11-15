package cz.czu.nick.chess.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import cz.czu.nick.chess.backend.model.User;
import cz.czu.nick.chess.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "registration")
@CssImport("./styles/shared-styles.css")
public class RegistrationView extends FormLayout {

    @Autowired
    private UserService userService;
    private final Binder<User> binder = new Binder<>();

    private TextField username = new TextField("login");
    private TextField passwordHash = new TextField("password");
    private Button btn = new Button("submit");

    public RegistrationView() {

        add(this.username);
        add(this.passwordHash);

        binder.forField(this.username).asRequired("User may not be empty")
                .bind(User::getUsername, User::setUsername);
        binder.forField(this.passwordHash).asRequired("User may not be empty")
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
        } else {
        }
    }
}