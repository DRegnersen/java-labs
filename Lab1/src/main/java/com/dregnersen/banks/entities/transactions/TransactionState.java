package com.dregnersen.banks.entities.transactions;

/**
 * The enum Transaction state.
 */
public enum TransactionState {
    /**
     * Untouched transaction state.
     */
    UNTOUCHED,
    /**
     * Begun transaction state.
     */
    BEGUN,
    /**
     * Rollbacked transaction state.
     */
    ROLLBACKED,
}
