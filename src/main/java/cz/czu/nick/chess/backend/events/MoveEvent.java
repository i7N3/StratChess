package cz.czu.nick.chess.backend.events;


import cz.czu.nick.chess.backend.model.Game;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class MoveEvent extends ApplicationEvent {

    private Game game;

    public MoveEvent(Object source, Game game) {
        super(source);
        this.game = game;
    }
}
