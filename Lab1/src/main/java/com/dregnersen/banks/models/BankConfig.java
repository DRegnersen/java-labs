package com.dregnersen.banks.models;

import com.dregnersen.banks.entities.banks.interestestimator.InterestEstimator;
import org.jetbrains.annotations.NotNull;

/**
 * The type Bank config.
 */
public record BankConfig(@NotNull Money outOfLimitCommission,
                         @NotNull Money unverifiedClientLimit,
                         @NotNull InterestEstimator interestEstimator) {
}
