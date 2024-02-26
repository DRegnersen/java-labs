package com.dregnersen.banks.entities.accounts.moneyaccumulators.impl;

import com.dregnersen.banks.entities.accounts.Account;
import com.dregnersen.banks.entities.accounts.moneyaccumulators.MoneyAccumulator;
import com.dregnersen.banks.models.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The type Standard money accumulator.
 */
public class StandardMoneyAccumulator implements MoneyAccumulator {
    private static final long ZERO_DAYS_NUMBER = 0;
    private static final BigDecimal ONE_HUNDRED_PERCENT = BigDecimal.valueOf(100);
    private BigDecimal interest;
    private final BigDecimal growthDaysNumber;
    private Money growth;

    {
        growth = Money.ZERO;
    }

    /**
     * Instantiates a new Standard money accumulator.
     *
     * @param interest         the interest
     * @param growthDaysNumber the growth days number
     */
    public StandardMoneyAccumulator(BigDecimal interest, long growthDaysNumber) {
        this.interest = interest;
        this.growthDaysNumber = validateGrowthDaysNumber(growthDaysNumber);
    }

    @Override
    public BigDecimal getInterest() {
        return interest;
    }

    @Override
    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    @Override
    public void accumulate(Account calculationAccount) {
        growth = new Money(growth.value().
                add(calculationAccount.getBalance().value().multiply(interest).
                        divide(growthDaysNumber.multiply(ONE_HUNDRED_PERCENT), RoundingMode.HALF_UP)));
    }

    @Override
    public void release(Account releaseAccount) {
        releaseAccount.setBalance(new Money(releaseAccount.getBalance().value().add(growth.value())));
        growth = Money.ZERO;
    }

    private BigDecimal validateGrowthDaysNumber(long growthDaysNumber) {
        if (growthDaysNumber <= ZERO_DAYS_NUMBER) {
            throw new IllegalArgumentException();
        }

        return BigDecimal.valueOf(growthDaysNumber);
    }
}
