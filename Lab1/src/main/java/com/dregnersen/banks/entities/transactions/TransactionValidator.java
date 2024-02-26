package com.dregnersen.banks.entities.transactions;

import com.dregnersen.banks.entities.accounts.Account;
import com.dregnersen.banks.models.Money;

import java.math.BigDecimal;

/**
 * The type Transaction validator.
 */
public final class TransactionValidator {
    private TransactionValidator() {
    }

    /**
     * Validate non negative money money.
     *
     * @param money the money
     * @return the money
     */
    public static Money validateNonNegativeMoney(Money money) {
        if (money.value().compareTo(BigDecimal.ZERO) < Money.VALUE_EQUALITY_STATE) {
            throw new IllegalArgumentException();
        }

        return money;
    }

    /**
     * Validate verification.
     *
     * @param account         the account
     * @param transactedMoney the transacted money
     */
    public static void validateVerification(Account account, Money transactedMoney) {
        Money unverifiedClientLimit = account.getClient().getBank().getUnverifiedClientLimit();

        if (transactedMoney.value().compareTo(unverifiedClientLimit.value()) > Money.VALUE_EQUALITY_STATE) {
            account.getClient().throwIfUnverified(IllegalArgumentException::new);
        }
    }
}
