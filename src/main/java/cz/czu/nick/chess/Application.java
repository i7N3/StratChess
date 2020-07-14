package cz.czu.nick.chess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

/*
    MVP

    Service Layer:

        - Model (Beans or POJOs @Entity)
        --------Service (Business logic)
        ----------------Repository (JPA)
        ---------------------Spring Data

    UI Layer:
        - View (any Vaadin Component (To make Tests easier the Presenter should implement a view interface))
        - Presenter (middle-man between Model and View) (aka Supervising Controller)

        In this case the View communicate directly to the Model using Data Binding and
        Presenter's responsibility is if necessary only for some complex logic.
*/




