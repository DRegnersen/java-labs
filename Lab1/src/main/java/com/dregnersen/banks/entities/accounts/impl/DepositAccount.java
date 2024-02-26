package com.dregnersen.banks.entities.accounts.impl;

import com.dregnersen.banks.entities.accounts.Account;
import com.dregnersen.banks.entities.accounts.EstimatedAccount;
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
 * The type Deposit account.
 */
public class DepositAccount implements EstimatedAccount {
    private static final long MIN_DAYS_NUMBER = 0;
    private final UUID id;
    private Client client;
    private Money balance;
    private long periodInDays;
    private final MoneyAccumulator moneyAccumulator;
    private final List<Transaction> transactions;

    {
        transactions = new ArrayList<>();
    }

    /**
     * Instantiates a new Deposit account.
     *
     * @param id               the id
     * @param client           the client
     * @param moneyAccumulator the money accumulator
     * @param periodInDays     the period in days
     * @param initialBalance   the initial balance
     */
    public DepositAccount(UUID id, Client client, MoneyAccumulator moneyAccumulator, long periodInDays, Money initialBalance) {
        this.id = id;
        this.client = client;
        this.periodInDays = periodInDays;
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
        if (balance.value().compareTo(this.balance.value()) < Money.VALUE_EQUALITY_STATE) {
            if (periodInDays > MIN_DAYS_NUMBER) {
                throw new IllegalArgumentException();
            }
        }
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
        if (periodInDays > MIN_DAYS_NUMBER) {
            moneyAccumulator.accumulate(this);
            periodInDays--;
        }
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
