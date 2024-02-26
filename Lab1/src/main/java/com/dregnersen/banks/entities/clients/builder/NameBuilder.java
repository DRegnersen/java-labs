package com.dregnersen.banks.entities.clients.builder;

/**
 * The interface Name builder.
 */
public interface NameBuilder {
    /**
     * With name surname builder.
     *
     * @param name the name
     * @return the surname builder
     */
    SurnameBuilder withName(String name);
}
