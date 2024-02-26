package com.dregnersen.banks.entities.accounts;

/**
 * The interface Limited account.
 */
public interface LimitedAccount extends Account {
    @Override
    default AccountType getAccountType() {
        return AccountType.LIMITED;
    }
}
