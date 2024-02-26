package com.dregnersen.banks.entities.transactions.impl;

import com.dregnersen.banks.entities.accounts.Account;
import com.dregnersen.banks.entities.transactions.Transaction;
import com.dregnersen.banks.entities.transactions.TransactionState;
import com.dregnersen.banks.entities.transactions.TransactionValidator;
import com.dregnersen.banks.models.Money;

/**
 * The type Withdraw transaction.
 */
public class WithdrawTransaction implements Transaction {
    private final Account account;
    private final Money transactedMoney;
    private final Money commission;
    private TransactionState state;

    /**
     * Instantiates a new Withdraw transaction.
     *
     * @param account         the account
     * @param transactedMoney the transacted money
     */
    public WithdrawTransaction(Account account, Money transactedMoney) {
        this(account, transactedMoney, Money.ZERO);
    }

    /**
     * Instantiates a new Withdraw transaction.
     *
     * @param account         the account
     * @param transactedMoney the transacted money
     * @param commission      the commission
     */
    public WithdrawTransaction(Account account, Money transactedMoney, Money commission) {
        TransactionValidator.validateVerification(account, transactedMoney);

        this.account = account;
        this.transactedMoney = TransactionValidator.validateNonNegativeMoney(transactedMoney);
        this.commission = TransactionValidator.validateNonNegativeMoney(commission);

        state = TransactionState.UNTOUCHED;
    }

    @Override
    public void begin() {
        if (!state.equals(TransactionState.UNTOUCHED)) {
            throw new IllegalStateException();
        }

        Money newWalletBalance =
                new Money(account.getBalance().value().subtract(transactedMoney.value()).subtract(commission.value()));

        account.setBalance(newWalletBalance);
        state = TransactionState.BEGUN;
    }

    @Override
    public void rollback() {
        if (!state.equals(TransactionState.BEGUN)) {
            throw new IllegalStateException();
        }

        Money rollbackedWalletBalance =
                new Money(account.getBalance().value().add(transactedMoney.value()).add(commission.value()));

        account.setBalance(rollbackedWalletBalance);
        state = TransactionState.ROLLBACKED;
    }
}
