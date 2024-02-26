package com.dregnersen.banks.centralbank.impl;

import com.dregnersen.banks.centralbank.CentralBank;
import com.dregnersen.banks.entities.banks.Bank;
import com.dregnersen.banks.entities.banks.impl.StandardBank;
import com.dregnersen.banks.models.BankConfig;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * The type Standard central bank.
 */
public class StandardCentralBank implements CentralBank {
    private final List<Bank> banks;

    {
        banks = new ArrayList<>();
    }

    @Override
    public List<Bank> getBanks() {
        return Collections.unmodifiableList(banks);
    }

    @Override
    public Optional<Bank> getBank(@NotNull UUID bankId) {
        return banks.stream().filter(bank -> bank.getId().equals(bankId)).findFirst();
    }

    @Override
    public Bank createBank(@NotNull String bankName, @NotNull BankConfig config) {
        Bank bank = new StandardBank(UUID.randomUUID(), bankName, config);
        banks.add(bank);
        return bank;
    }
}
