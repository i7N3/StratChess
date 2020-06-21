package cz.czu.nick.chess.backend.service;

import org.springframework.stereotype.Service;

@Service
public class ChessService {
    public String greet(String name) {
        if (name == null || name.isEmpty()) {
            return "Hello anonymous user";
        } else {
            return "Hello " + name;
        }
    }
}
