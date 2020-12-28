package cz.czu.nick.chess.backend.service;

import com.vaadin.flow.shared.Registration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Broadcaster {
    static Executor executor = Executors.newSingleThreadExecutor();

    static Map<String, Consumer<String>> listeners = new HashMap<>();

    public static synchronized Registration register(
            String username, Consumer<String> listener) {
        listeners.put(username, listener);

        return () -> {
            synchronized (Broadcaster.class) {
                listeners.remove(username);
            }
        };
    }

    public static synchronized void broadcast(String username, String message) {
        Consumer<String> l = listeners.get(username);
        executor.execute(() -> l.accept(message));
//
//        for (Consumer<String> listener : listeners.values()) {
//
//        }
    }
}
