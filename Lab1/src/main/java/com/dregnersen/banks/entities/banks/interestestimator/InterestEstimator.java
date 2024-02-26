package com.dregnersen.banks.entities.banks.interestestimator;

import com.dregnersen.banks.entities.banks.interestestimator.builder.CategoryBuilder;
import com.dregnersen.banks.entities.banks.interestestimator.builder.TerminalBuilder;
import com.dregnersen.banks.models.Money;

import java.math.BigDecimal;
import java.util.*;

/**
 * The type Interest estimator.
 */
public class InterestEstimator {
    private final List<Money> categories;
    private final Map<BigDecimal, BigDecimal> categoryValueToInterestMap;

    private InterestEstimator(List<Money> categories, Map<BigDecimal, BigDecimal> categoryValueToInterestMap) {
        this.categories = categories;
        this.categoryValueToInterestMap = categoryValueToInterestMap;
    }

    /**
     * Estimate sum big decimal.
     *
     * @param sum the sum
     * @return the big decimal
     */
    public BigDecimal estimateSum(Money sum) {
        Money properCategory = categories.stream().takeWhile(category -> category.value().compareTo(sum.value()) <= Money.VALUE_EQUALITY_STATE).
                reduce((firstCategory, secondCategory) -> secondCategory).orElseThrow(NoSuchElementException::new);

        return Optional.ofNullable(categoryValueToInterestMap.get(properCategory.value())).orElseThrow(IllegalStateException::new);
    }

    /**
     * Gets builder.
     *
     * @param firstCategoryLowerBound the first category lower bound
     * @return the builder
     */
    public static CategoryBuilder getBuilder(Money firstCategoryLowerBound) {
        return new InterestMapperBuilder(firstCategoryLowerBound);
    }

    /**
     * The type Interest mapper builder.
     */
    static class InterestMapperBuilder implements CategoryBuilder, TerminalBuilder {
        private final List<Money> categories;
        private final Map<BigDecimal, BigDecimal> categoryValueToInterestMap;
        private Money lastCategoryLowerBound;

        {
            categories = new ArrayList<>();
            categoryValueToInterestMap = new HashMap<>();
        }

        /**
         * Instantiates a new Interest mapper builder.
         *
         * @param firstCategoryLowerBound the first category lower bound
         */
        public InterestMapperBuilder(Money firstCategoryLowerBound) {
            this.lastCategoryLowerBound = firstCategoryLowerBound;
            categories.add(firstCategoryLowerBound);
        }

        @Override
        public CategoryBuilder withCategory(Money categoryRange, BigDecimal interest) {
            categoryValueToInterestMap.put(lastCategoryLowerBound.value(), interest);

            lastCategoryLowerBound = new Money(lastCategoryLowerBound.value().add(categoryRange.value()));
            categories.add(lastCategoryLowerBound);
            return this;
        }

        @Override
        public TerminalBuilder withLastCategory(BigDecimal interest) {
            categoryValueToInterestMap.put(lastCategoryLowerBound.value(), interest);
            return this;
        }

        @Override
        public InterestEstimator build() {
            if (categoryValueToInterestMap.isEmpty()) {
                throw new IllegalStateException();
            }

            return new InterestEstimator(categories, categoryValueToInterestMap);
        }
    }
}
