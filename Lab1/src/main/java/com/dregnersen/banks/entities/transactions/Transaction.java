package com.dregnersen.banks.entities.transactions;

/**
 * The interface Transaction.
 */
public interface Transaction {
    /**
     * Begin.
     */
    void begin();

    /**
     * Rollback.
     */
    void rollback();
}
