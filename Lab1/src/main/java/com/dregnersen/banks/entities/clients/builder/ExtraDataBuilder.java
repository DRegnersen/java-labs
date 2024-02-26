package com.dregnersen.banks.entities.clients.builder;

import com.dregnersen.banks.entities.clients.Client;

/**
 * The interface Extra data builder.
 */
public interface ExtraDataBuilder {
    /**
     * With passport data extra data builder.
     *
     * @param passportData the passport data
     * @return the extra data builder
     */
    ExtraDataBuilder withPassportData(String passportData);

    /**
     * With address extra data builder.
     *
     * @param address the address
     * @return the extra data builder
     */
    ExtraDataBuilder withAddress(String address);

    /**
     * Build client.
     *
     * @return the client
     */
    Client build();
}
