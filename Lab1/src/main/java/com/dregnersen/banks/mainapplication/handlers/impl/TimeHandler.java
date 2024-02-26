package com.dregnersen.banks.mainapplication.handlers.impl;

import com.dregnersen.banks.mainapplication.handlers.Handler;
import com.dregnersen.banks.mainapplication.handlers.HandlerSelector;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * The type Time handler.
 */
public class TimeHandler implements Handler {
    private HandlerSelector selector;
    private final BufferedReader reader;

    /**
     * Instantiates a new Time handler.
     *
     * @param reader the reader
     */
    public TimeHandler(BufferedReader reader) {
        this.reader = reader;
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
        System.out.println("<TIME>");
        String command = reader.readLine();

        switch (command) {
            case "/home" -> selector.selectHandler(command);
            case "/day_forward" -> selector.getContext().chronometer().dayForward();
            case "/month_forward" -> selector.getContext().chronometer().monthForward();
            default -> System.out.println(Handler.NO_SUCH_COMMAND_MESSAGE);
        }
    }
}
