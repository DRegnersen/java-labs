package com.dregnersen.banks.entities.clients.builder;

import com.dregnersen.banks.entities.banks.ModifiableBank;

/**
 * The interface Bank builder.
 */
public interface BankBuilder {
    /**
     * With bank name builder.
     *
     * @param bank the bank
     * @return the name builder
     */
    NameBuilder withBank(ModifiableBank bank);
}
