package cz.czu.nick.chess.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
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

    private H1 title = new H1("Sign in");
    private TextField usrField = new TextField("Username");
    private PasswordField pwdField = new PasswordField("Password");
    private Button signInBtn = new Button("Sign in");
    private Notification notification = new Notification("SigIn Error", 4000);

    @Autowired
    public LoginView(CustomRequestCache requestCache, AuthenticationManager authenticationManager) {
        this.requestCache = requestCache;
        this.authenticationManager = authenticationManager;

        addClassName("form");

        usrField.setRequired(true);
        pwdField.setRequired(true);
        binder.forField(usrField).asRequired("User may not be empty")
                .bind(User::getUsername, User::setUsername);
        binder.forField(pwdField).asRequired("Password may not be empty")
                .bind(User::getPasswordHash, User::setPasswordHash);

        signInBtn.addClickListener(this::signIn);
        signInBtn.addClassName("form__submit");

        RouterLink signUpLink = new RouterLink("Sign Up", RegistrationView.class);
        signUpLink.addClassName("form__link");

        add(title, usrField, pwdField, signInBtn, signUpLink);
        add(
                new H6("Test credentials"),
                new Span("user1;password"),
                new Span("user2;password")
        );
    }

    private void signIn(ClickEvent e) {
        try {
            // try to authenticate with given credentials, should always
            // return not null or throw an {@link AuthenticationException}
            final Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    usrField.getValue(),
                                    pwdField.getValue()));


            // if authentication was successful we will update
            // the security context and redirect to the page requested first
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UI.getCurrent().navigate(requestCache.resolveRedirectUrl());
        } catch (AuthenticationException ex) {
            notification.open();
        }
    }
}