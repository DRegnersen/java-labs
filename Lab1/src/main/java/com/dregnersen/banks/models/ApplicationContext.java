package com.dregnersen.banks.models;

import com.dregnersen.banks.centralbank.CentralBank;
import com.dregnersen.banks.entities.banks.interestestimator.InterestEstimator;
import com.dregnersen.banks.tools.chronometers.Chronometer;
import com.dregnersen.banks.tools.idsimplifier.IdSimplifier;
import org.jetbrains.annotations.NotNull;

/**
 * The type Application context.
 */
public record ApplicationContext(@NotNull CentralBank centralBank,
                                 @NotNull Chronometer chronometer,
                                 @NotNull IdSimplifier accountIdSimplifier,
                                 @NotNull InterestEstimator standardInterestEstimator,
                                 @NotNull Money standardOutOfLimitCommission,
                                 @NotNull Money standardUnverifiedClientLimit) {
}
