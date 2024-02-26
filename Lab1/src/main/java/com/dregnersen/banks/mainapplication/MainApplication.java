package com.dregnersen.banks.mainapplication;

import com.dregnersen.banks.centralbank.CentralBank;
import com.dregnersen.banks.centralbank.impl.StandardCentralBank;
import com.dregnersen.banks.entities.banks.interestestimator.InterestEstimator;
import com.dregnersen.banks.mainapplication.handlers.HandlerSelector;
import com.dregnersen.banks.mainapplication.handlers.impl.*;
import com.dregnersen.banks.models.ApplicationContext;
import com.dregnersen.banks.models.Money;
import com.dregnersen.banks.tools.chronometers.Chronometer;
import com.dregnersen.banks.tools.chronometers.impl.CentralBankChronometer;
import com.dregnersen.banks.tools.idsimplifier.IdSimplifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

/**
 * The type Main application.
 */
public final class MainApplication {
    private static final ApplicationContext context;

    static {
        CentralBank centralBank = new StandardCentralBank();
        Chronometer chronometer = new CentralBankChronometer(centralBank);

        InterestEstimator standardInterestEstimator = InterestEstimator.getBuilder(Money.ZERO).
                withCategory(new Money(BigDecimal.valueOf(50000)), BigDecimal.valueOf(3)).
                withCategory(new Money(BigDecimal.valueOf(50000)), BigDecimal.valueOf(3.5)).
                withLastCategory(BigDecimal.valueOf(4)).build();

        context = new ApplicationContext(centralBank,
                chronometer,
                new IdSimplifier(),
                standardInterestEstimator,
                new Money(BigDecimal.valueOf(100)),
                new Money(BigDecimal.valueOf(5000)));
    }

    private MainApplication() {
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            HandlerSelector selector = new HandlerSelector(context);
            selector.addHandler("/home", new HomeHandler(bufferedReader));
            selector.addHandler("/bank", new BankHandler(bufferedReader));
            selector.addHandler("/client", new ClientHandler(bufferedReader));
            selector.addHandler("/time", new TimeHandler(bufferedReader));
            selector.addHandler("/account", new AccountHandler(bufferedReader));

            selector.selectHandler("/home");
            selector.getCurrentHandler().run();

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
