package com.dregnersen.banks.mainapplication.handlers.impl;

import com.dregnersen.banks.entities.accounts.Account;
import com.dregnersen.banks.entities.clients.Client;
import com.dregnersen.banks.mainapplication.ClientInfoRequester;
import com.dregnersen.banks.mainapplication.MoneyRequester;
import com.dregnersen.banks.mainapplication.handlers.Handler;
import com.dregnersen.banks.mainapplication.handlers.HandlerSelector;
import com.dregnersen.banks.models.Money;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * The type Account handler.
 */
public class AccountHandler implements Handler {
    private HandlerSelector selector;
    private final BufferedReader reader;

    /**
     * Instantiates a new Account handler.
     *
     * @param reader the reader
     */
    public AccountHandler(BufferedReader reader) {
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
        System.out.println("<ACCOUNT>");
        String command = reader.readLine();

        switch (command) {
            case "/home" -> selector.selectHandler(command);
            case "/create" -> {
                try {
                    Optional<Client> foundClient = ClientInfoRequester.requestClient(reader, selector.getContext());

                    if (foundClient.isEmpty()) {
                        System.out.println("There is no such client in this bank!");
                        throw new IllegalStateException();
                    }

                    System.out.print("Select account type: (debit/credit/deposit) ");
                    String accountType = reader.readLine();

                    Account account;
                    switch (accountType) {
                        case "debit" -> {
                            System.out.print("Interest, %: ");
                            try {
                                account = foundClient.get().createDebitAccount(BigDecimal.
                                        valueOf(Double.parseDouble(reader.readLine())));
                            } catch (NumberFormatException e) {
                                System.out.println("Incorrect interest format!");
                                throw new IllegalStateException();
                            }
                        }
                        case "credit" -> {
                            System.out.print("Credit limit: ");
                            account = foundClient.get().createCreditAccount(MoneyRequester.request(reader).
                                    orElseThrow(IllegalStateException::new));
                        }
                        case "deposit" -> {
                            System.out.print("Period, days: ");
                            long periodInDays = Long.parseLong(reader.readLine());
                            System.out.print("Initial balance: ");
                            Money initialBalance = MoneyRequester.request(reader).orElseThrow(IllegalStateException::new);
                            account = foundClient.get().createDepositAccount(periodInDays, initialBalance);
                        }
                        default -> {
                            System.out.println("There is no such account type!");
                            throw new IllegalStateException();
                        }
                    }

                    long simpleId = selector.getContext().accountIdSimplifier().createSimpleId(account.getId());
                    System.out.println("Account with id (" + simpleId +
                            ") has been successfully created");

                } catch (IllegalStateException ignored) {
                }
            }

            case "/refill" -> {
                try {
                    Account account = requestAccount();
                    System.out.print("Refilled money: ");
                    account.refill(MoneyRequester.request(reader).orElseThrow(IllegalStateException::new));
                    System.out.println("Transaction has been successfully committed");
                } catch (IllegalStateException ignored) {
                }
            }

            case "/withdraw" -> {
                try {
                    Account account = requestAccount();
                    System.out.print("Withdrew money: ");
                    try {
                        account.withdraw(MoneyRequester.request(reader).orElseThrow(IllegalStateException::new));
                        System.out.println("Transaction has been successfully committed");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Not enough money or operation is prohibited!");
                    }
                } catch (IllegalStateException ignored) {
                }
            }

            case "/transfer" -> {
                try {
                    System.out.println("(Sender)");
                    Account senderAccount = requestAccount();
                    System.out.println("(Recipient)");
                    Account recipientAccount = requestAccount();
                    System.out.print("Transferred money: ");
                    Money transferredMoney = MoneyRequester.request(reader).orElseThrow(IllegalStateException::new);
                    try {
                        senderAccount.transfer(recipientAccount, transferredMoney);
                        System.out.println("Transaction has been successfully committed");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Not enough money or operation is prohibited!");
                    }
                } catch (IllegalStateException ignored) {
                }
            }

            case "/balance" -> {
                try {
                    Account account = requestAccount();
                    System.out.println(account.getBalance().value() + " $");
                } catch (IllegalStateException ignored) {
                }
            }

            default -> System.out.println(Handler.NO_SUCH_COMMAND_MESSAGE);
        }
    }

    private Account requestAccount() throws IOException {
        System.out.print("Account id: ");
        UUID accountId;

        try {
            accountId = selector.getContext().accountIdSimplifier().
                    getInitialId(Long.parseLong(reader.readLine()));
        } catch (IllegalArgumentException e) {
            System.out.println("There is no such account!");
            throw new IllegalStateException();
        }

        Optional<Account> foundAccount = getAccountsStream().
                filter(account -> account.getId().equals(accountId)).findFirst();

        if (foundAccount.isEmpty()) {
            System.out.println("Unexpected error occurred!");
            throw new IllegalStateException();
        }

        return foundAccount.get();
    }

    private Stream<Account> getAccountsStream() {
        return selector.getContext().centralBank().getBanks().stream().
                flatMap(bank -> bank.getClients().stream()).
                flatMap(client -> client.getAccounts().stream());
    }
}
