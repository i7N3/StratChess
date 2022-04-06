# Chess game using the Vaadin and Spring Boot

This program is a PvP game implemented as Web Application.

### Few words about application
The app was created for educational purposes only. During development, attention was paid to simplicity (OOP), performance was ignored. The game engine is written in pure Java, in some places were used decorators from the lombok library. The chessboard is represented as matrix (8x8) and [FEN notation](https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation).

### Application architecture
![Application architecture](/architecture.png)

### Getting ready for production
- Add `vaadin.productionMode` with `true` value to `pom.xml`.
- Execute the `mvn vaadin: build-frontend` command.
- Create .jar file using the `mvn clean package -Pproduction` command.

The application archive is located along the path `./target/chess-1.0-SNAPSHOT.jar`.
To run the application, execute the command `java -jar chess-1.0-SNAPSHOT.jar`.

It's also possible improve the program execution and save logs using a script:
```
 #!/bin/bash
nohup java -jar chess-1.0-SNAPSHOT.jar > chess.log 2>&1 & echo $! > save_pid.txt
```

GLHF!
