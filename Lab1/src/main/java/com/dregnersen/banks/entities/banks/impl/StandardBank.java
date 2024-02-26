package com.dregnersen.banks.entities.banks.impl;

import com.dregnersen.banks.entities.accounts.Account;
import com.dregnersen.banks.entities.accounts.AccountType;
import com.dregnersen.banks.entities.accounts.impl.DepositAccount;
import com.dregnersen.banks.entities.banks.ModifiableBank;
import com.dregnersen.banks.entities.banks.interestestimator.InterestEstimator;
import com.dregnersen.banks.entities.clients.Client;
import com.dregnersen.banks.entities.clients.ClientBuilder;
import com.dregnersen.banks.entities.clients.SubscriptionState;
import com.dregnersen.banks.entities.clients.builder.IdBuilder;
import com.dregnersen.banks.entities.clients.builder.NameBuilder;
import com.dregnersen.banks.models.BankConfig;
import com.dregnersen.banks.models.Money;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.*;

/**
 * The type Standard bank.
 */
public class StandardBank implements ModifiableBank {
    private final static String LIMITED_COMMISSION_CHANGES_MESSAGE = "Out-of-limit commission has been changed";
    private final static String ESTIMATED_INTEREST_CHANGES_MESSAGE = "Interest policy for estimated accounts has been changed";

    private final UUID id;
    private final String name;
    private Money outOfLimitCommission;
    private final Money unverifiedClientLimit;
    private InterestEstimator interestEstimator;
    private final List<Client> clients;

    {
        clients = new ArrayList<>();
    }

    /**
     * Instantiates a new Standard bank.
     *
     * @param id     the id
     * @param name   the name
     * @param config the config
     */
    public StandardBank(UUID id, String name, BankConfig config) {
        this.id = id;
        this.name = name;
        this.outOfLimitCommission = config.outOfLimitCommission();
        this.unverifiedClientLimit = config.unverifiedClientLimit();
        this.interestEstimator = config.interestEstimator();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Money getOutOfLimitCommission() {
        return outOfLimitCommission;
    }

    @Override
    public void setOutOfLimitCommission(@NotNull Money outOfLimitCommission) {
        this.outOfLimitCommission = outOfLimitCommission;

        clients.stream().filter(client -> client.getAccounts().stream().
                        anyMatch(account -> account.getAccountType() == AccountType.LIMITED)).
                filter(client -> client.getNotificationState() == SubscriptionState.SUBSCRIBED).
                forEach(client -> client.getNotifier().notify(LIMITED_COMMISSION_CHANGES_MESSAGE));
    }

    @Override
    public Money getUnverifiedClientLimit() {
        return unverifiedClientLimit;
    }

    @Override
    public InterestEstimator getInterestEstimator() {
        return interestEstimator;
    }

    @Override
    public void setInterestEstimator(@NotNull InterestEstimator interestEstimator) {
        this.interestEstimator = interestEstimator;

        clients.forEach(client -> client.getAccounts().stream().
                filter(account -> account.getClass() == DepositAccount.class).
                forEach(account -> account.setInterest(interestEstimator.estimateSum(account.getBalance()))));

        clients.stream().filter(client -> client.getAccounts().stream().
                        anyMatch(account -> account.getAccountType() == AccountType.ESTIMATED)).
                filter(client -> client.getNotificationState() == SubscriptionState.SUBSCRIBED).
                forEach(client -> client.getNotifier().notify(ESTIMATED_INTEREST_CHANGES_MESSAGE));
    }

    @Override
    public List<Client> getClients() {
        return Collections.unmodifiableList(clients);
    }

    @Override
    public void addClient(Client client) {
        if (clients.stream().anyMatch(containedClient -> containedClient.getId().equals(client.getId()))) {
            throw new IllegalArgumentException();
        }
        clients.add(client);
    }

    @Override
    public void removeClient(@NotNull UUID clientId) {
        if (!clients.removeIf(client -> client.getId().equals(clientId))) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public NameBuilder getClientBuilder() {
        IdBuilder builder = new ClientBuilder();
        return builder.withId(UUID.randomUUID()).withBank(this);
    }

    @Override
    public void updateInterest(@NotNull UUID accountId, @NotNull BigDecimal newInterest) {
        Account updatedAccount = clients.stream().flatMap(client -> client.getAccounts().stream()).
                filter(account -> account.getId().equals(accountId)).findFirst().
                orElseThrow(IllegalArgumentException::new);

        updatedAccount.setInterest(newInterest);
        if (updatedAccount.getClient().getNotificationState() == SubscriptionState.SUBSCRIBED) {
            updatedAccount.getClient().getNotifier().notify("Interest of account-" + accountId + " has been changed");
        }
    }
}
