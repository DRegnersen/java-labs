package com.dregnersen.banks.entities.accounts.impl;

import com.dregnersen.banks.entities.accounts.Account;
import com.dregnersen.banks.entities.accounts.moneyaccumulators.MoneyAccumulator;
import com.dregnersen.banks.entities.clients.Client;
import com.dregnersen.banks.entities.transactions.Transaction;
import com.dregnersen.banks.entities.transactions.impl.RefillTransaction;
import com.dregnersen.banks.entities.transactions.impl.TransferTransaction;
import com.dregnersen.banks.entities.transactions.impl.WithdrawTransaction;
import com.dregnersen.banks.models.Money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * The type Debit account.
 */
public class DebitAccount implements Account {
    private final UUID id;
    private Client client;
    private Money balance;
    private final MoneyAccumulator moneyAccumulator;
    private final List<Transaction> transactions;

    {
        transactions = new ArrayList<>();
    }

    /**
     * Instantiates a new Debit account.
     *
     * @param id               the id
     * @param client           the client
     * @param moneyAccumulator the money accumulator
     */
    public DebitAccount(UUID id, Client client, MoneyAccumulator moneyAccumulator) {
        this(id, client, moneyAccumulator, Money.ZERO);
    }

    /**
     * Instantiates a new Debit account.
     *
     * @param id               the id
     * @param client           the client
     * @param moneyAccumulator the money accumulator
     * @param initialBalance   the initial balance
     */
    public DebitAccount(UUID id, Client client, MoneyAccumulator moneyAccumulator, Money initialBalance) {
        this.id = id;
        this.client = client;
        this.moneyAccumulator = moneyAccumulator;
        this.balance = initialBalance;
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
        return balance;
    }

    @Override
    public void setBalance(Money balance) {
        this.balance = balance;
    }

    @Override
    public BigDecimal getInterest() {
        return moneyAccumulator.getInterest();
    }

    @Override
    public void setInterest(BigDecimal interest) {
        moneyAccumulator.setInterest(interest);
    }

    @Override
    public void refill(Money money) {
        RefillTransaction transaction = new RefillTransaction(this, money);
        transactions.add(transaction);

        transaction.begin();
    }

    @Override
    public void withdraw(Money money) {
        validateCreditworthiness(money);

        WithdrawTransaction transaction = new WithdrawTransaction(this, money);
        transactions.add(transaction);

        transaction.begin();
    }

    @Override
    public void transfer(Account recipientAccount, Money money) {
        validateCreditworthiness(money);

        TransferTransaction transaction = new TransferTransaction(this, recipientAccount, money);
        transactions.add(transaction);

        transaction.begin();
    }

    @Override
    public void doDailyActivity() {
        moneyAccumulator.accumulate(this);
    }

    @Override
    public void doMonthlyActivity() {
        moneyAccumulator.release(this);
    }

    private void validateCreditworthiness(Money creditedMoney) {
        if (creditedMoney.value().compareTo(balance.value()) > Money.VALUE_EQUALITY_STATE) {
            throw new IllegalArgumentException();
        }
    }
}
