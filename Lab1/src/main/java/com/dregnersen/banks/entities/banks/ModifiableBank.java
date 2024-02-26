package com.dregnersen.banks.entities.banks;

import com.dregnersen.banks.entities.clients.Client;

/**
 * The interface Modifiable bank.
 */
public interface ModifiableBank extends Bank {
    /**
     * Add client.
     *
     * @param client the client
     */
    void addClient(Client client);
}
