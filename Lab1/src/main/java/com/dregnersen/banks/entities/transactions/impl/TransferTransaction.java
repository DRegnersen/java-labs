package com.dregnersen.banks.entities.transactions.impl;

import com.dregnersen.banks.entities.accounts.Account;
import com.dregnersen.banks.entities.transactions.Transaction;
import com.dregnersen.banks.entities.transactions.TransactionState;
import com.dregnersen.banks.entities.transactions.TransactionValidator;
import com.dregnersen.banks.models.Money;

/**
 * The type Transfer transaction.
 */
public class TransferTransaction implements Transaction {
    private final Account senderAccount;
    private final Account recipientAccount;
    private final Money transactedMoney;
    private final Money commission;
    private TransactionState state;

    /**
     * Instantiates a new Transfer transaction.
     *
     * @param senderAccount    the sender account
     * @param recipientAccount the recipient account
     * @param transactedMoney  the transacted money
     */
    public TransferTransaction(Account senderAccount, Account recipientAccount, Money transactedMoney) {
        this(senderAccount, recipientAccount, transactedMoney, Money.ZERO);
    }

    /**
     * Instantiates a new Transfer transaction.
     *
     * @param senderAccount    the sender account
     * @param recipientAccount the recipient account
     * @param transactedMoney  the transacted money
     * @param commission       the commission
     */
    public TransferTransaction(Account senderAccount, Account recipientAccount, Money transactedMoney, Money commission) {
        TransactionValidator.validateVerification(senderAccount, transactedMoney);

        this.senderAccount = senderAccount;
        this.recipientAccount = recipientAccount;
        this.transactedMoney = TransactionValidator.validateNonNegativeMoney(transactedMoney);
        this.commission = TransactionValidator.validateNonNegativeMoney(commission);

        state = TransactionState.UNTOUCHED;
    }

    @Override
    public void begin() {
        if (!state.equals(TransactionState.UNTOUCHED)) {
            throw new IllegalStateException();
        }

        Money newSenderWalletBalance =
                new Money(senderAccount.getBalance().value().subtract(transactedMoney.value()).subtract(commission.value()));

        Money newRecipientWalletBalance =
                new Money(recipientAccount.getBalance().value().add(transactedMoney.value()));

        senderAccount.setBalance(newSenderWalletBalance);
        recipientAccount.setBalance(newRecipientWalletBalance);
        state = TransactionState.BEGUN;
    }

    @Override
    public void rollback() {
        if (!state.equals(TransactionState.BEGUN)) {
            throw new IllegalStateException();
        }

        Money rollbackedSenderWalletBalance =
                new Money(senderAccount.getBalance().value().add(transactedMoney.value()).add(commission.value()));

        Money rollbackedRecipientWalletBalance =
                new Money(recipientAccount.getBalance().value().subtract(transactedMoney.value()));

        senderAccount.setBalance(rollbackedSenderWalletBalance);
        recipientAccount.setBalance(rollbackedRecipientWalletBalance);
        state = TransactionState.ROLLBACKED;
    }
}
