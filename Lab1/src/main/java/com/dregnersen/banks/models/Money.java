package com.dregnersen.banks.models;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * The type Money.
 */
public record Money(@NotNull BigDecimal value) {
    /**
     * The constant VALUE_EQUALITY_STATE.
     */
    public static final int VALUE_EQUALITY_STATE = 0;
    /**
     * The constant ZERO.
     */
    public static final Money ZERO = new Money(BigDecimal.ZERO);
}
