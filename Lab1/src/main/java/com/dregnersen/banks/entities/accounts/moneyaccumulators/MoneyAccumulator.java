package com.dregnersen.banks.entities.accounts.moneyaccumulators;

import com.dregnersen.banks.entities.accounts.Account;

import java.math.BigDecimal;

/**
 * The interface Money accumulator.
 */
public interface MoneyAccumulator {
    /**
     * Gets interest.
     *
     * @return the interest
     */
    BigDecimal getInterest();

    /**
     * Sets interest.
     *
     * @param interest the interest
     */
    void setInterest(BigDecimal interest);

    /**
     * Accumulate.
     *
     * @param calculationAccount the calculation account
     */
    void accumulate(Account calculationAccount);

    /**
     * Release.
     *
     * @param releaseAccount the release account
     */
    void release(Account releaseAccount);
}
