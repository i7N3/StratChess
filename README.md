# StartChess ♟️️

## Introduction 📖

Welcome to StartChess! This player-versus-player chess game combines Vaadin's engaging UI with Spring Boot's powerful backend, offering an immersive experience for chess fans and learners alike. It's more than just a game; it's a showcase of modern web technologies in a classic, strategic context.

## Overview 🌐

At the heart of StartChess is a commitment to simplicity and clean object-oriented programming principles. The game engine, developed in Java, utilizes a traditional 8x8 matrix to represent the chessboard, and games are managed using the [FEN notation](https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation) notation. This setup is perfect for those looking to understand the intersection of classic game theory and modern programming practices.

## Application Architecture

![Application architecture](/architecture.png)
_The architecture diagram above illustrates the modular design of StartChess._

## Prerequisites 🛠️

Before diving into StartChess, ensure you have:

-   Java (preferably JDK 11 or newer) installed.
-   Maven for managing project dependencies and builds.
-   An IDE of your choice (optional) for editing and running the application.

## Testing and Validation 🧪

Tehre are a series of unit tests to ensure the reliability and accuracy of the game mechanics:

-   **Figure Move Validation:** Tests each chess piece's movement according to the rules.
-   **Board State Validation:** Ensures the board accurately reflects the current game state.
-   **Game Rules Validation:** Verifies that standard chess rules are enforced throughout gameplay.

To run tests, use `mvn test`.

## Preparing for Production 🚀

-   **Enable Production Mode:** Set `vaadin.productionMode` to true in `pom.xml`.
-   **Build Frontend:** Execute `mvn vaadin:build-frontend`` to prepare the front-end assets.
-   **Create Executable Jar:** Generate `chess-1.0-SNAPSHOT.jar` with `mvn clean package -Pproduction`

**Running the Application:**

-   Locate the application jar at `./target/chess-1.0-SNAPSHOT.jar`.
-   Launch it using `java -jar chess-1.0-SNAPSHOT.jar`.

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
