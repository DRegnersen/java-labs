package com.dregnersen.banks.entities.clients;

import com.dregnersen.banks.entities.accounts.Account;
import com.dregnersen.banks.entities.accounts.impl.CreditAccount;
import com.dregnersen.banks.entities.accounts.impl.DebitAccount;
import com.dregnersen.banks.entities.accounts.impl.DepositAccount;
import com.dregnersen.banks.entities.banks.Bank;
import com.dregnersen.banks.entities.notifiers.Notifier;
import com.dregnersen.banks.models.Money;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * The interface Client.
 */
public interface Client {
    /**
     * Gets id.
     *
     * @return the id
     */
    UUID getId();

    /**
     * Gets bank.
     *
     * @return the bank
     */
    Bank getBank();

    /**
     * Gets name.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets surname.
     *
     * @return the surname
     */
    String getSurname();

    /**
     * Gets accounts.
     *
     * @return the accounts
     */
    List<Account> getAccounts();

    /**
     * Gets notifier.
     *
     * @return the notifier
     */
    Notifier getNotifier();

    /**
     * Gets notification state.
     *
     * @return the notification state
     */
    SubscriptionState getNotificationState();

    /**
     * Sets notification state.
     *
     * @param notificationState the notification state
     */
    void setNotificationState(SubscriptionState notificationState);

    /**
     * Create debit account.
     *
     * @param interest the interest
     * @return the debit account
     */
    DebitAccount createDebitAccount(BigDecimal interest);

    /**
     * Create credit account.
     *
     * @param creditLimit the credit limit
     * @return the credit account
     */
    CreditAccount createCreditAccount(Money creditLimit);

    /**
     * Create deposit account.
     *
     * @param periodInDays   the period in days
     * @param initialBalance the initial balance
     * @return the deposit account
     */
    DepositAccount createDepositAccount(Long periodInDays, Money initialBalance);

    /**
     * Throw if unverified.
     *
     * @param <T>               the type parameter
     * @param exceptionSupplier the exception supplier
     * @throws T the t
     */
    default <T extends Throwable> void throwIfUnverified(Supplier<? extends T> exceptionSupplier) throws T {
    }
}
