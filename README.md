# chess-poc ♟️️

## Overview 🌐

Chess-poc is an engaging player-versus-player chess game developed as a web application. It uses Vaadin and Spring Boot for an immersive and responsive user experience. Aimed at demonstrating the effective combination of modern web technologies with classic game logic, this project serves as an educational tool and a platform for chess enthusiasts.

## Educational Focus and Game Engine Details 🎓

The focus during development was to prioritize simplicity and clean object-oriented programming. The game engine is crafted in pure Java. The chessboard is represented as matrix (8x8) and [FEN notation](https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation).

## Application architecture

![Application architecture](/architecture.png)

## Testing and Validation 🧪

Tehre are a series of unit tests to ensure the reliability and accuracy of the game mechanics:

-   Figure Move Validation
-   Board State Validation
-   Game rules validation

To run tests, use `mvn test`.

## Preparing for Production 🚀

-   **Enable Production Mode:** Add `vaadin.productionMode` set to true in `pom.xml`.
-   **Build Frontend:** Run `mvn vaadin:build-frontend`.
-   **Create Executable Jar:** Use `mvn clean package -Pproduction` to generate `chess-1.0-SNAPSHOT.jar`

**Running the Application:**

-   The application jar is located at `./target/chess-1.0-SNAPSHOT.jar`.
-   Launch it with `java -jar chess-1.0-SNAPSHOT.jar`.

For enhanced execution and logging:

```
 #!/bin/bash
nohup java -jar chess-1.0-SNAPSHOT.jar > chess.log 2>&1 & echo $! > save_pid.txt
```

## Contributing and Further Development 💡

Contributions are warmly welcomed! Feel free to submit PRs for bug fixes, feature enhancements, or aesthetic improvements.

Current TODOs:

-   Implement additional endgame scenarios.
-   Refine UI/UX for an enhanced gameplay experience.

## Have Fun and Good Luck! 🎉

Dive in, explore the game, and contribute. Your insights and improvements are vital in evolving this educational tool!

GLHF!
