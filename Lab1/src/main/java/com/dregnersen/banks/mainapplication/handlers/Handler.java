package com.dregnersen.banks.mainapplication.handlers;

import java.io.IOException;

/**
 * The interface Handler.
 */
public interface Handler {
    /**
     * The constant NO_SUCH_COMMAND_MESSAGE.
     */
    String NO_SUCH_COMMAND_MESSAGE = "There is no such command in this scope!";

    /**
     * Gets selector.
     *
     * @return the selector
     */
    HandlerSelector getSelector();

    /**
     * Sets selector.
     *
     * @param selector the selector
     */
    void setSelector(HandlerSelector selector);

    /**
     * Handle.
     *
     * @throws IOException the io exception
     */
    void handle() throws IOException;

    /**
     * Gets handling status.
     *
     * @return the handling status
     */
    default HandlingStatus getHandlingStatus() {
        return HandlingStatus.IN_PROCESS;
    }

    /**
     * Run.
     *
     * @throws IOException the io exception
     */
    default void run() throws IOException {
        if (getSelector() == null) {
            throw new IllegalStateException();
        }
        handle();
        if (getHandlingStatus() != HandlingStatus.EXIT_REQUESTED) {
            getSelector().getCurrentHandler().run();
        }
    }
}
