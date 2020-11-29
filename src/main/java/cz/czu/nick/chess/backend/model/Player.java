package cz.czu.nick.chess.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {

    private Color color;
    private String username;

    public Player(String username, Color color) {
        this.color = color;
        this.username = username;
    }

}
