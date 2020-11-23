package cz.czu.nick.chess.app.security;

import cz.czu.nick.chess.ui.LoginView;
import cz.czu.nick.chess.ui.RegistrationView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Configures spring security, doing the following:
 * <li>Bypass security checks for static resources,</li>
 * <li>Restrict access to the application, allowing only logged in users,</li>
 * <li>Set up the login form</li>
 */
@EnableWebSecurity
@Configuration
// https://vaadin.com/learn/tutorials/modern-web-apps-with-spring-boot-and-vaadin/adding-a-login-screen-to-a-vaadin-app-with-spring-security
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String LOGOUT_SUCCESS_URL = "/";
    private static final String REGISTRATION_URL = "/registration";

    @Bean
    @Override
    // Since SpringBoot 2 the authentication manager bean has to be exposed manually.
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    // We also have to expose the custom request cache to access it in our login view.
    public CustomRequestCache requestCache() {
        return new CustomRequestCache();
    }

    /**
     * Require login to access internal pages and configure login form.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Not using Spring CSRF here to be able to use plain HTML for the login page
        http.csrf().disable()

                // Register our CustomRequestCache that saves unauthorized access attempts, so
                // the user is redirected after login.
                .requestCache().requestCache(new CustomRequestCache())

                // Restrict access to our application.
                .and().authorizeRequests()

                // Allow all flow internal requests.
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

                // Allow request registration page for anonymous users.
                .antMatchers("/" + RegistrationView.ROUTE).permitAll()

                // Allow all requests by logged in users.
                .anyRequest().authenticated()

                // Configure the login page.
                .and().formLogin().loginPage("/" + LoginView.ROUTE).permitAll()

                // Configure logout
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }

    /**
     * Allows access to static resources, bypassing Spring security.
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                // Vaadin Flow static resources
                "/VAADIN/**",

                // the standard favicon URI
                "/favicon.ico",

                // the robots exclusion standard
                "/robots.txt",

                // web application manifest
                "/manifest.webmanifest",
                "/sw.js",
                "/offline-page.html",

                // icons and images
                "/icons/**",
                "/images/**",

                // (development mode) static resources
                "/frontend/**",

                // (development mode) webjars
                "/webjars/**",

                // (development mode) H2 debugging console
                "/h2-console/**",

                // (production mode) static resources
                "/frontend-es5/**", "/frontend-es6/**");
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        // typical logged in user with some privileges
        UserDetails user =
                User.withUsername("user")
                        .password("{noop}password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}