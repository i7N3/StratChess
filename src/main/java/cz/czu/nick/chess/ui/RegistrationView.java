package cz.czu.nick.chess.ui;

import com.vaadin.flow.component.ClickEvent;
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
    private Notification notification = new Notification("Registration Error", 4000);

    @Autowired
    public RegistrationView(UserService userService) {
        this.userService = userService;

        addClassName("form");

        usrField.setRequired(true);
        pwdField.setRequired(true);
        binder.forField(usrField).asRequired("User may not be empty")
                .bind(User::getUsername, User::setUsername);
        binder.forField(pwdField).asRequired("Password may not be empty")
                .bind(User::getPasswordHash, User::setPasswordHash);

        RouterLink signInLink = new RouterLink("Sign in", LoginView.class);
        signInLink.getStyle().set("text-align", "center");

        add(new H1("Registration"), usrField, pwdField, createButton(), signInLink);
    }

    private Button createButton() {
        Button button = new Button("Sign Up", this::auth);
        button.addClassName("form__submit");
        return button;
    }

    private void auth(ClickEvent event) {
        User user = new User();
        if (binder.writeBeanIfValid(user)) {
            userService.saveUser(user);
            UI.getCurrent().navigate(LoginView.class);
        } else {
            notification.open();
        }
    }
}