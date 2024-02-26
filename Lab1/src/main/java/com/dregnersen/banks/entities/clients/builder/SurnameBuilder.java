package com.dregnersen.banks.entities.clients.builder;

/**
 * The interface Surname builder.
 */
public interface SurnameBuilder {
    /**
     * With surname extra data builder.
     *
     * @param surname the surname
     * @return the extra data builder
     */
    ExtraDataBuilder withSurname(String surname);
}
