package com.dregnersen.banks.entities.banks.interestestimator.builder;

import com.dregnersen.banks.entities.banks.interestestimator.InterestEstimator;

/**
 * The interface Terminal builder.
 */
public interface TerminalBuilder {
    /**
     * Build interest estimator.
     *
     * @return the interest estimator
     */
    InterestEstimator build();
}
