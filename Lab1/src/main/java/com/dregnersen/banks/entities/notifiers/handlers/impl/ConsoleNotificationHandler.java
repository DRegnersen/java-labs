package com.dregnersen.banks.entities.notifiers.handlers.impl;

import com.dregnersen.banks.entities.notifiers.handlers.NotificationHandler;

import java.util.UUID;

/**
 * The type Console notification handler.
 */
public class ConsoleNotificationHandler implements NotificationHandler {
    private final UUID id;

    /**
     * Instantiates a new Console notification handler.
     *
     * @param id the id
     */
    public ConsoleNotificationHandler(UUID id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    @Override
    public void acceptNotification(String message) {
        System.out.println("CNH-" + id.toString() + ": " + message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConsoleNotificationHandler that = (ConsoleNotificationHandler) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
