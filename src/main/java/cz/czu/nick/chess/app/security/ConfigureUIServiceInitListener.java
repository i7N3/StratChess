package cz.czu.nick.chess.app.security;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import cz.czu.nick.chess.ui.LoginView;
import cz.czu.nick.chess.ui.RegistrationView;
import org.springframework.stereotype.Component;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::authenticateNavigation);
        });
    }

    /**
     * Reroutes the user if (s)he is not authorized to access the view.
     *
     * @param event before navigation event with event details
     */
    private void authenticateNavigation(BeforeEnterEvent event) {

        // Ignores the login view itself and
        // Only redirects if user is not logged in.
        if (!LoginView.class.equals(event.getNavigationTarget())
                && !RegistrationView.class.equals(event.getNavigationTarget())
                && !SecurityUtils.isUserLoggedIn()) {
            // Actual rerouting the login view if needed.
            event.rerouteTo(LoginView.class);
        }
    }

}