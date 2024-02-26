package com.dregnersen.banks.entities.notifiers;

import com.dregnersen.banks.entities.notifiers.handlers.NotificationHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Notifier.
 */
public class Notifier {
    private final List<NotificationHandler> handlers;

    {
        handlers = new ArrayList<>();
    }

    /**
     * Add handler.
     *
     * @param handler the handler
     */
    public void addHandler(@NotNull NotificationHandler handler) {
        if (handlers.contains(handler)) {
            throw new IllegalArgumentException();
        }
        handlers.add(handler);
    }

    /**
     * Remove handler.
     *
     * @param handler the handler
     */
    public void removeHandler(@NotNull NotificationHandler handler) {
        if (!handlers.remove(handler)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Notify.
     *
     * @param message the message
     */
    public void notify(@NotNull String message){
        handlers.forEach(handler -> handler.acceptNotification(message));
    }
}
