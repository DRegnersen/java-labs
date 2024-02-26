package com.dregnersen.banks.entities.accounts;

/**
 * The interface Estimated account.
 */
public interface EstimatedAccount extends Account {
    @Override
    default AccountType getAccountType() {
        return AccountType.ESTIMATED;
    }
}
