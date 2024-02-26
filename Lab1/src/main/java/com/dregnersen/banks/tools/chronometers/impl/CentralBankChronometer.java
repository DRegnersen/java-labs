package com.dregnersen.banks.tools.chronometers.impl;

import com.dregnersen.banks.centralbank.CentralBank;
import com.dregnersen.banks.entities.accounts.Account;
import com.dregnersen.banks.tools.chronometers.Chronometer;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * The type Central bank chronometer.
 */
public class CentralBankChronometer implements Chronometer {
    private static final int DAYS_IN_MONTH_NUMBER = 30;
    private static final int ALL_DAYS_PASSED_NUMBER = 0;
    private final CentralBank centralBank;
    private int currentMonthDaysPassed;

    {
        currentMonthDaysPassed = DAYS_IN_MONTH_NUMBER;
    }

    /**
     * Instantiates a new Central bank chronometer.
     *
     * @param centralBank the central bank
     */
    public CentralBankChronometer(@NotNull CentralBank centralBank) {
        this.centralBank = centralBank;
    }

    public void dayForward() {
        currentMonthDaysPassed--;

        if (currentMonthDaysPassed == ALL_DAYS_PASSED_NUMBER) {
            getAccountsStream().forEach(Account::doMonthlyActivity);
            currentMonthDaysPassed = DAYS_IN_MONTH_NUMBER;
        }
        getAccountsStream().forEach(Account::doDailyActivity);
    }

    public void monthForward() {
        for (int day = ALL_DAYS_PASSED_NUMBER; day < DAYS_IN_MONTH_NUMBER; day++) {
            dayForward();
        }
    }

    private Stream<Account> getAccountsStream() {
        return centralBank.getBanks().stream().flatMap(bank -> bank.getClients().stream()).
                flatMap(client -> client.getAccounts().stream());
    }
}
