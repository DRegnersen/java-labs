package com.dregnersen.banks.centralbank;

import com.dregnersen.banks.entities.banks.Bank;
import com.dregnersen.banks.models.BankConfig;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The interface Central bank.
 */
public interface CentralBank {
    /**
     * Gets banks.
     *
     * @return the banks
     */
    List<Bank> getBanks();

    /**
     * Gets bank.
     *
     * @param bankId the bank id
     * @return the bank
     */
    Optional<Bank> getBank(UUID bankId);

    /**
     * Create bank.
     *
     * @param bankName the bank name
     * @param config   the config
     * @return the bank
     */
    Bank createBank(String bankName, BankConfig config);
}
