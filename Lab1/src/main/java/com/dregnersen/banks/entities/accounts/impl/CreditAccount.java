package com.dregnersen.banks.entities.accounts.impl;

import com.dregnersen.banks.entities.accounts.Account;
import com.dregnersen.banks.entities.accounts.LimitedAccount;
import com.dregnersen.banks.entities.clients.Client;
import com.dregnersen.banks.entities.transactions.Transaction;
import com.dregnersen.banks.entities.transactions.impl.RefillTransaction;
import com.dregnersen.banks.entities.transactions.impl.TransferTransaction;
import com.dregnersen.banks.entities.transactions.impl.WithdrawTransaction;
import com.dregnersen.banks.models.Money;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * The type Credit account.
 */
public class CreditAccount implements LimitedAccount {
    private final UUID id;
    private Client client;
    private Money debt;
    private final Money creditLimit;
    private final List<Transaction> transactions;

    {
        debt = Money.ZERO;
        transactions = new ArrayList<>();
    }

    /**
     * Instantiates a new Credit account.
     *
     * @param id          the id
     * @param client      the client
     * @param creditLimit the credit limit
     */
    public CreditAccount(UUID id, Client client, Money creditLimit) {
        this.id = id;
        this.client = client;
        this.creditLimit = creditLimit;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    @Override
    public Money getBalance() {
        return new Money(creditLimit.value().subtract(debt.value()));
    }

    @Override
    public void setBalance(Money balance) {
        debt = new Money(creditLimit.value().subtract(balance.value()));
    }

    @Override
    public void refill(Money money) {
        RefillTransaction transaction = new RefillTransaction(this, money);
        transactions.add(transaction);

        transaction.begin();
    }

    @Override
    public void withdraw(Money money) {
        Money commission = client.getBank().getOutOfLimitCommission();

        WithdrawTransaction transaction = (isCreditworthiness(money)) ?
                new WithdrawTransaction(this, money) :
                new WithdrawTransaction(this, money, commission);

        transactions.add(transaction);

        transaction.begin();
    }

    @Override
    public void transfer(Account recipientAccount, Money money) {
        Money commission = client.getBank().getOutOfLimitCommission();

        TransferTransaction transaction = (isCreditworthiness(money)) ?
                new TransferTransaction(this, recipientAccount, money) :
                new TransferTransaction(this, recipientAccount, money, commission);

        transactions.add(transaction);

        transaction.begin();
    }

    private boolean isCreditworthiness(Money creditedMoney) {
        return creditLimit.value().subtract(debt.value()).compareTo(creditedMoney.value()) > Money.VALUE_EQUALITY_STATE;
    }
}
