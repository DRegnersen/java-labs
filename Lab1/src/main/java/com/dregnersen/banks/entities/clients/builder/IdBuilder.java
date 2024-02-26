package com.dregnersen.banks.entities.clients.builder;

import java.util.UUID;

/**
 * The interface Id builder.
 */
public interface IdBuilder {
    /**
     * With id bank builder.
     *
     * @param id the id
     * @return the bank builder
     */
    BankBuilder withId(UUID id);
}
