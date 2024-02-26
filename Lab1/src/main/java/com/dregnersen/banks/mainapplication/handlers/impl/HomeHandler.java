package com.dregnersen.banks.mainapplication.handlers.impl;

import com.dregnersen.banks.mainapplication.handlers.HandlerSelector;
import com.dregnersen.banks.mainapplication.handlers.Handler;
import com.dregnersen.banks.mainapplication.handlers.HandlingStatus;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * The type Home handler.
 */
public class HomeHandler implements Handler {
    private HandlerSelector selector;
    private final BufferedReader reader;
    private HandlingStatus handlingStatus;

    {
        handlingStatus = HandlingStatus.IN_PROCESS;
    }

    /**
     * Instantiates a new Home handler.
     *
     * @param reader the reader
     */
    public HomeHandler(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public HandlingStatus getHandlingStatus() {
        return handlingStatus;
    }

    @Override
    public HandlerSelector getSelector() {
        return selector;
    }

    @Override
    public void setSelector(HandlerSelector selector) {
        this.selector = selector;
    }

    @Override
    public void handle() throws IOException {
        System.out.println("<HOME>");
        String command = reader.readLine();

        if (command.equals("/exit")) {
            handlingStatus = HandlingStatus.EXIT_REQUESTED;
            System.out.println("Exiting...");
            return;
        }

        try {
            selector.selectHandler(command);
        } catch (IllegalArgumentException e) {
            System.out.println(Handler.NO_SUCH_COMMAND_MESSAGE);
        }
    }
}
