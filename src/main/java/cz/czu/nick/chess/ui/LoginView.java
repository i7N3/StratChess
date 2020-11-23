package cz.czu.nick.chess.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import cz.czu.nick.chess.app.security.CustomRequestCache;
import cz.czu.nick.chess.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;


@Route(value = LoginView.ROUTE)
@CssImport("./styles/shared-styles.css")
public class LoginView extends FormLayout implements BeforeEnterObserver {

    public static final String ROUTE = "login";

//    private LoginForm login = new LoginForm();

    // Gets the dialog
    private LoginForm login = new LoginForm();

    @Autowired
    private UserService userService;
//    private final Binder<User> binder = new Binder<>();
//
//    private TextField username = new TextField("login");
//    private TextField password = new TextField("password");

    public LoginView(AuthenticationManager authenticationManager, CustomRequestCache requestCache) {

        // Sets the action aka the endpoint Spring Security is expecting the form data at.
        login.setAction("login");
        add(login);
        add(new Anchor(RegistrationView.ROUTE, "Registration"));

        login.addLoginListener(e -> {
            try {
                // try to authenticate with given credentials, should always
                // return not null or throw an {@link AuthenticationException}
                final Authentication authentication =
                        authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                        e.getUsername(),
                                        e.getPassword()));

                // if authentication was successful we will update
                // the security context and redirect to the page requested first
                SecurityContextHolder.getContext().setAuthentication(authentication);
                UI.getCurrent().navigate(requestCache.resolveRedirectUrl());

            } catch (AuthenticationException ex) {
                // show default error message
                login.setError(true);
            }
        });

//        add(username);
//        add(password);
//        binder.forField(username).asRequired("User may not be empty")
//                .bind(User::getUsername, User::setUsername);
//        binder.forField(password).asRequired("User may not be empty")
//                .bind(User::getPasswordHash, User::setPasswordHash);
//        add(createButton());
    }

//    private Button createButton() {
//        Button button = new Button("Sign Up", event -> find());
//        return button;
//    }

//    private void find() {
//        User user = new User();
//        if (binder.writeBeanIfValid(user)) {
//            String username = userService.validateUser(user);
//            VaadinSession.getCurrent().setAttribute("username", username);
//
////            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(true);
////            UI.getCurrent().navigate(MainView.class);
//        } else {
//        }
//    }

//    @Override
//    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
//
//    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}