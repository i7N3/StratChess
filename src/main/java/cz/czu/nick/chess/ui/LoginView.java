package cz.czu.nick.chess.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.*;
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

    private LoginForm login = new LoginForm();


    private UserService userService;

    @Autowired
    public LoginView(AuthenticationManager authenticationManager, CustomRequestCache requestCache, UserService userService) {

        this.userService = userService;

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
    }

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