package com.dregnersen.banks.entities.accounts;

import com.dregnersen.banks.entities.clients.Client;
import com.dregnersen.banks.entities.transactions.Transaction;
import com.dregnersen.banks.models.Money;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * The interface Account.
 */
public interface Account {
    /**
     * Gets id.
     *
     * @return the id
     */
    UUID getId();

    /**
     * Gets client.
     *
     * @return the client
     */
    Client getClient();

    /**
     * Sets client.
     *
     * @param client the client
     */
    void setClient(Client client);

    /**
     * Gets transactions.
     *
     * @return the transactions
     */
    List<Transaction> getTransactions();

    /**
     * Gets balance.
     *
     * @return the balance
     */
    Money getBalance();

    /**
     * Sets balance.
     *
     * @param balance the balance
     */
    void setBalance(Money balance);

    /**
     * Refill.
     *
     * @param money the money
     */
    void refill(Money money);

    /**
     * Withdraw.
     *
     * @param money the money
     */
    void withdraw(Money money);

    /**
     * Transfer.
     *
     * @param recipientAccount the recipient account
     * @param money            the money
     */
    void transfer(Account recipientAccount, Money money);

    /**
     * Gets interest.
     *
     * @return the interest
     */
    default BigDecimal getInterest() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets interest.
     *
     * @param interest the interest
     */
    default void setInterest(BigDecimal interest) {
        throw new UnsupportedOperationException();
    }

    /**
     * Do daily activity.
     */
    default void doDailyActivity() {
    }

    /**
     * Do monthly activity.
     */
    default void doMonthlyActivity() {
    }

    /**
     * Gets account type.
     *
     * @return the account type
     */
    default AccountType getAccountType() {
        return AccountType.COMMON;
    }
}
