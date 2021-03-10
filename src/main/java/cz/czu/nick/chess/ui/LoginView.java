package cz.czu.nick.chess.ui;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import cz.czu.nick.chess.app.security.CustomRequestCache;
import cz.czu.nick.chess.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;


@Tag("form")
@Route(value = LoginView.ROUTE)
@CssImport("./styles/shared-styles.css")
public class LoginView extends Div {

    public static final String ROUTE = "login";

    private CustomRequestCache requestCache;
    private AuthenticationManager authenticationManager;

    private final Binder<User> binder = new Binder<>();

    private H1 title = new H1("Přihlášení");
    private TextField usrField = new TextField("Přezdívka");
    private PasswordField pwdField = new PasswordField("Heslo");
    private Button signInBtn = new Button("Přihlásit se");
    private Notification notification = new Notification("Oops, něco se povedlo, zkus znovu!", 4000);

    @Autowired
    public LoginView(CustomRequestCache requestCache, AuthenticationManager authenticationManager) {
        this.requestCache = requestCache;
        this.authenticationManager = authenticationManager;

        addClassName("form");

        usrField.setRequired(true);
        pwdField.setRequired(true);
        usrField.setAutofocus(true);
        binder.forField(usrField).asRequired("Přezdívka nesmí být prázdný")
                .withValidator(
                        username -> username.length() >= 4,
                        "Přezdívka musí obsahovat alespoň 4 znaků")
                .bind(User::getUsername, User::setUsername);
        binder.forField(pwdField).asRequired("Heslo nesmí být prázdný")
                .withValidator(
                        pwd -> pwd.length() >= 6,
                        "Heslo musí obsahovat alespoň 6 znaků")
                .bind(User::getPasswordHash, User::setPasswordHash);

        usrField.addKeyPressListener(Key.ENTER, this::signIn);
        pwdField.addKeyPressListener(Key.ENTER, this::signIn);
        
        signInBtn.addClickListener(this::signIn);
        signInBtn.addClassName("form__submit");

        RouterLink signUpLink = new RouterLink("Registrace", RegistrationView.class);
        signUpLink.addClassName("form__link");

        add(title, usrField, pwdField, signInBtn, signUpLink);
        add(
                new H6("Testovací údaje (školní projekt): "),
                new Span("user1;password"),
                new Span("user2;password")
        );
    }

    private void signIn(ComponentEvent e) {
        try {
            // try to authenticate with given credentials, should always
            // return not null or throw an {@link AuthenticationException}
            final Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    usrField.getValue(),
                                    pwdField.getValue()));

            notification.setText("Přihlášení proběhlo úspěšně");
            notification.open();

            // if authentication was successful we will update
            // the security context and redirect to the page requested first
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UI.getCurrent().navigate(requestCache.resolveRedirectUrl());
        } catch (AuthenticationException ex) {
            notification.setText("Oops, něco se povedlo, zkus znovu!");
            notification.open();
        }
    }
}