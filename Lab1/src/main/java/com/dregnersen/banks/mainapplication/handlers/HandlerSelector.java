package com.dregnersen.banks.mainapplication.handlers;

import com.dregnersen.banks.models.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Handler selector.
 */
public class HandlerSelector {
    private final ApplicationContext context;
    private final Map<String, Handler> commandToHandlerMap;
    private Handler currentHandler;

    {
        commandToHandlerMap = new HashMap<>();
    }

    /**
     * Instantiates a new Handler selector.
     *
     * @param context the context
     */
    public HandlerSelector(ApplicationContext context) {
        this.context = context;
    }

    /**
     * Gets context.
     *
     * @return the context
     */
    public ApplicationContext getContext() {
        return context;
    }

    /**
     * Gets current handler.
     *
     * @return the current handler
     */
    public Handler getCurrentHandler() {
        return currentHandler;
    }

    /**
     * Add handler.
     *
     * @param invokingCommand the invoking command
     * @param handler         the handler
     */
    public void addHandler(String invokingCommand, Handler handler) {
        commandToHandlerMap.put(invokingCommand, handler);
        handler.setSelector(this);
    }

    /**
     * Select handler.
     *
     * @param command the command
     */
    public void selectHandler(String command) {
        if (!commandToHandlerMap.containsKey(command)) {
            throw new IllegalArgumentException();
        }
        currentHandler = commandToHandlerMap.get(command);
    }
}
