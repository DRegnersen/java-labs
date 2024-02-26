package com.dregnersen.banks.mainapplication.handlers.impl;

import com.dregnersen.banks.entities.banks.Bank;
import com.dregnersen.banks.mainapplication.MoneyRequester;
import com.dregnersen.banks.mainapplication.handlers.Handler;
import com.dregnersen.banks.mainapplication.handlers.HandlerSelector;
import com.dregnersen.banks.models.BankConfig;
import com.dregnersen.banks.models.Money;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

/**
 * The type Bank handler.
 */
public class BankHandler implements Handler {
    private HandlerSelector selector;
    private final BufferedReader reader;

    /**
     * Instantiates a new Bank handler.
     *
     * @param reader the reader
     */
    public BankHandler(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public HandlerSelector getSelector() {
        return selector;
    }

    @Override
    public void setSelector(HandlerSelector selector) {
        this.selector = selector;
    }

    @Override
    public void handle() throws IOException {
        System.out.println("<BANK>");
        String command = reader.readLine();

        switch (command) {
            case "/home" -> selector.selectHandler(command);
            case "/create" -> {
                try {
                    System.out.print("Bank's name: ");
                    String bankName = reader.readLine();

                    Optional<Bank> foundBank = selector.getContext().centralBank().getBanks().stream().
                            filter(existingBank -> existingBank.getName().equals(bankName)).findFirst();

                    if (foundBank.isPresent()) {
                        System.out.println("Bank with such name already exists!");
                        throw new IllegalStateException();
                    }

                    System.out.print("Do you want to continue setting up? (y/n): ");
                    String answer = reader.readLine();

                    if (answer.equals("y")) {
                        System.out.print("Bank's out-of-limit commission: ");
                        Money outOfLimitCommission = MoneyRequester.request(reader).orElseThrow(IllegalStateException::new);

                        System.out.print("Bank's operation limit for unverified clients: ");
                        Money unverifiedClientLimit = MoneyRequester.request(reader).orElseThrow(IllegalStateException::new);

                        selector.getContext().centralBank().createBank(bankName,
                                new BankConfig(outOfLimitCommission,
                                        unverifiedClientLimit,
                                        selector.getContext().standardInterestEstimator()));
                    } else {

                        selector.getContext().centralBank().createBank(bankName,
                                new BankConfig(selector.getContext().standardOutOfLimitCommission(),
                                        selector.getContext().standardUnverifiedClientLimit(),
                                        selector.getContext().standardInterestEstimator()));
                    }

                    System.out.println("Bank '" + bankName + "' has been successfully created");
                } catch (IllegalStateException ignored) {
                }
            }

            default -> System.out.println(Handler.NO_SUCH_COMMAND_MESSAGE);
        }
    }
}

