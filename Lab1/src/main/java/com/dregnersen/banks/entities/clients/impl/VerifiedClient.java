package com.dregnersen.banks.entities.clients.impl;

import com.dregnersen.banks.entities.accounts.Account;
import com.dregnersen.banks.entities.accounts.impl.CreditAccount;
import com.dregnersen.banks.entities.accounts.impl.DebitAccount;
import com.dregnersen.banks.entities.accounts.impl.DepositAccount;
import com.dregnersen.banks.entities.accounts.moneyaccumulators.impl.StandardMoneyAccumulator;
import com.dregnersen.banks.entities.banks.Bank;
import com.dregnersen.banks.entities.clients.Client;
import com.dregnersen.banks.entities.clients.SubscriptionState;
import com.dregnersen.banks.entities.notifiers.Notifier;
import com.dregnersen.banks.models.Money;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * The type Verified client.
 */
public class VerifiedClient implements Client {
    private final UUID id;
    private final Bank bank;
    private final String name;
    private final String surname;
    private final String passportData;
    private final String address;
    private SubscriptionState notificationState;
    private final Notifier notifier;
    private final List<Account> accounts;

    {
        notificationState = SubscriptionState.UNSUBSCRIBED;
        accounts = new ArrayList<>();
    }

    /**
     * Instantiates a new Verified client.
     *
     * @param unverifiedClient the unverified client
     * @param passportData     the passport data
     * @param address          the address
     */
    public VerifiedClient(UnverifiedClient unverifiedClient, String passportData, String address) {
        this.id = unverifiedClient.getId();
        this.bank = unverifiedClient.getBank();
        this.name = unverifiedClient.getName();
        this.surname = unverifiedClient.getSurname();
        this.notificationState = unverifiedClient.getNotificationState();
        this.notifier = unverifiedClient.getNotifier();
        this.passportData = passportData;
        this.address = address;
        accounts.addAll(unverifiedClient.getAccounts());
    }

    /**
     * Instantiates a new Verified client.
     *
     * @param id           the id
     * @param bank         the bank
     * @param name         the name
     * @param surname      the surname
     * @param passportData the passport data
     * @param address      the address
     * @param notifier     the notifier
     */
    public VerifiedClient(UUID id, Bank bank, String name, String surname, String passportData, String address, Notifier notifier) {
        this.id = id;
        this.bank = bank;
        this.name = name;
        this.surname = surname;
        this.passportData = passportData;
        this.address = address;
        this.notifier = notifier;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Bank getBank() {
        return bank;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    /**
     * Gets passport data.
     *
     * @return the passport data
     */
    public String getPassportData() {
        return passportData;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    @Override
    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    @Override
    public Notifier getNotifier() {
        return notifier;
    }

    @Override
    public SubscriptionState getNotificationState() {
        return notificationState;
    }

    @Override
    public void setNotificationState(@NotNull SubscriptionState notificationState) {
        this.notificationState = notificationState;
    }

    @Override
    public DebitAccount createDebitAccount(@NotNull BigDecimal interest) {
        DebitAccount account = new DebitAccount(UUID.randomUUID(), this, new StandardMoneyAccumulator(interest, Bank.GROWTH_DAYS_NUMBER));
        accounts.add(account);
        return account;
    }

    @Override
    public CreditAccount createCreditAccount(@NotNull Money creditLimit) {
        CreditAccount account = new CreditAccount(UUID.randomUUID(), this, creditLimit);
        accounts.add(account);
        return account;
    }

    @Override
    public DepositAccount createDepositAccount(@NotNull Long periodInDays, @NotNull Money initialBalance) {
        DepositAccount account = new DepositAccount(
                UUID.randomUUID(),
                this,
                new StandardMoneyAccumulator(bank.getInterestEstimator().estimateSum(initialBalance), Bank.GROWTH_DAYS_NUMBER),
                periodInDays,
                initialBalance);

        accounts.add(account);
        return account;
    }
}
