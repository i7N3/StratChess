package cz.czu.nick.chess.ui;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import cz.czu.nick.chess.backend.model.User;
import cz.czu.nick.chess.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = RegistrationView.ROUTE)
@CssImport("./styles/shared-styles.css")
public class RegistrationView extends FormLayout {

    public static final String ROUTE = "registration";

    private UserService userService;
    private final Binder<User> binder = new Binder<>();

    private TextField usrField = new TextField("Username");
    private PasswordField pwdField = new PasswordField("Password");
    private Notification notification = new Notification("Oops, something went wrong", 4000);

    @Autowired
    public RegistrationView(UserService userService) {
        this.userService = userService;

        addClassName("form");

        usrField.setRequired(true);
        pwdField.setRequired(true);
        usrField.setAutofocus(true);

        binder.forField(usrField).asRequired("PUsername cannot be empty")
                .withValidator(
                        username -> username.length() >= 4,
                        "Username should containt at least 4 chars")
                .bind(User::getUsername, User::setUsername);
        binder.forField(pwdField).asRequired("Password cannot be empty")
                .withValidator(
                    pwd -> pwd.length() >= 6,
                    "Password should contain at least 6 chars")
                .bind(User::getPasswordHash, User::setPasswordHash);

        RouterLink signInLink = new RouterLink("Sign in", LoginView.class);
        signInLink.addClassName("form__link");

        usrField.addKeyPressListener(Key.ENTER, this::register);
        pwdField.addKeyPressListener(Key.ENTER, this::register);

        add(new H1("Registration"), usrField, pwdField, createButton(), signInLink);
    }

    private Button createButton() {
        Button button = new Button("Register", this::register);
        button.addClassName("form__submit");
        return button;
    }

    private void register(ComponentEvent event) {
        User user = new User();

        if (userService.existsUsersByUser(user)) {
            notification.setText("User already exists");
            notification.open();
            return;
        }

        if (binder.writeBeanIfValid(user)) {
            notification.setText("User registered");
            notification.open();
            userService.saveUser(user);
            UI.getCurrent().navigate(LoginView.class);
        } else {
            notification.setText("Try again but with different data");
            notification.open();
        }
    }
}