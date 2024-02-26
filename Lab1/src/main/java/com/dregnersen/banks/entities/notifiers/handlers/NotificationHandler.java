package com.dregnersen.banks.entities.notifiers.handlers;

/**
 * The interface Notification handler.
 */
public interface NotificationHandler {
    /**
     * Accept notification.
     *
     * @param message the message
     */
    void acceptNotification(String message);
}
