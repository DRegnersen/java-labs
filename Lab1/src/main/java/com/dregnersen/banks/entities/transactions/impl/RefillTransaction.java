package com.dregnersen.banks.entities.transactions.impl;

import com.dregnersen.banks.entities.accounts.Account;
import com.dregnersen.banks.entities.transactions.Transaction;
import com.dregnersen.banks.entities.transactions.TransactionState;
import com.dregnersen.banks.entities.transactions.TransactionValidator;
import com.dregnersen.banks.models.Money;

/**
 * The type Refill transaction.
 */
public class RefillTransaction implements Transaction {
    private final Account account;
    private final Money transactedMoney;
    private TransactionState state;

    /**
     * Instantiates a new Refill transaction.
     *
     * @param account         the account
     * @param transactedMoney the transacted money
     */
    public RefillTransaction(Account account, Money transactedMoney) {
        this.account = account;
        this.transactedMoney = TransactionValidator.validateNonNegativeMoney(transactedMoney);
        state = TransactionState.UNTOUCHED;
    }

    @Override
    public void begin() {
        if (!state.equals(TransactionState.UNTOUCHED)) {
            throw new IllegalStateException();
        }
        Money newWalletBalance =
                new Money(account.getBalance().value().add(transactedMoney.value()));

        account.setBalance(newWalletBalance);
        state = TransactionState.BEGUN;
    }

    @Override
    public void rollback() {
        if (!state.equals(TransactionState.BEGUN)) {
            throw new IllegalStateException();
        }
        Money rollbackedWalletBalance =
                new Money(account.getBalance().value().subtract(transactedMoney.value()));

        account.setBalance(rollbackedWalletBalance);
        state = TransactionState.ROLLBACKED;
    }
}
