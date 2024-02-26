package com.dregnersen.banks.entities.banks;

import com.dregnersen.banks.entities.banks.interestestimator.InterestEstimator;
import com.dregnersen.banks.entities.clients.Client;
import com.dregnersen.banks.entities.clients.builder.NameBuilder;
import com.dregnersen.banks.models.Money;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * The interface Bank.
 */
public interface Bank {
    /**
     * The constant GROWTH_DAYS_NUMBER.
     */
    long GROWTH_DAYS_NUMBER = 365;

    /**
     * Gets id.
     *
     * @return the id
     */
    UUID getId();

    /**
     * Gets name.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets out of limit commission.
     *
     * @return the out of limit commission
     */
    Money getOutOfLimitCommission();

    /**
     * Sets out of limit commission.
     *
     * @param outOfLimitCommission the out of limit commission
     */
    void setOutOfLimitCommission(Money outOfLimitCommission);

    /**
     * Gets unverified client limit.
     *
     * @return the unverified client limit
     */
    Money getUnverifiedClientLimit();

    /**
     * Gets interest estimator.
     *
     * @return the interest estimator
     */
    InterestEstimator getInterestEstimator();

    /**
     * Sets interest estimator.
     *
     * @param interestEstimator the interest estimator
     */
    void setInterestEstimator(InterestEstimator interestEstimator);

    /**
     * Gets clients.
     *
     * @return the clients
     */
    List<Client> getClients();

    /**
     * Remove client.
     *
     * @param clientId the client id
     */
    void removeClient(UUID clientId);

    /**
     * Gets client builder.
     *
     * @return the client builder
     */
    NameBuilder getClientBuilder();

    /**
     * Update interest.
     *
     * @param accountId   the account id
     * @param newInterest the new interest
     */
    void updateInterest(UUID accountId, BigDecimal newInterest);
}
