package com.dregnersen.banks.entities.banks.interestestimator.builder;

import com.dregnersen.banks.models.Money;

import java.math.BigDecimal;

/**
 * The interface Category builder.
 */
public interface CategoryBuilder {
    /**
     * With category category builder.
     *
     * @param categoryRange the category range
     * @param interest      the interest
     * @return the category builder
     */
    CategoryBuilder withCategory(Money categoryRange, BigDecimal interest);

    /**
     * With last category terminal builder.
     *
     * @param interest the interest
     * @return the terminal builder
     */
    TerminalBuilder withLastCategory(BigDecimal interest);
}
