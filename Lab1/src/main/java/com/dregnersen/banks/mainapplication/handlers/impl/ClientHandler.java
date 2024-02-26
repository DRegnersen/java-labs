package com.dregnersen.banks.mainapplication.handlers.impl;

import com.dregnersen.banks.entities.clients.Client;
import com.dregnersen.banks.entities.clients.builder.NameBuilder;
import com.dregnersen.banks.entities.clients.impl.UnverifiedClient;
import com.dregnersen.banks.mainapplication.ClientInfoRequester;
import com.dregnersen.banks.mainapplication.handlers.Handler;
import com.dregnersen.banks.mainapplication.handlers.HandlerSelector;
import com.dregnersen.banks.models.ClientInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

/**
 * The type Client handler.
 */
public class ClientHandler implements Handler {
    private HandlerSelector selector;
    private final BufferedReader reader;

    /**
     * Instantiates a new Client handler.
     *
     * @param reader the reader
     */
    public ClientHandler(BufferedReader reader) {
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
        System.out.println("<CLIENT>");
        String command = reader.readLine();

        switch (command) {
            case "/home" -> selector.selectHandler(command);
            case "/create" -> {
                try {
                    ClientInfo clientInfo = ClientInfoRequester.requestInfo(reader, selector.getContext());

                    Optional<Client> foundClient = clientInfo.bank().getClients().stream().
                            filter(client -> client.getName().equals(clientInfo.name())).
                            filter(client -> client.getSurname().equals(clientInfo.surname())).findFirst();

                    if (foundClient.isPresent()) {
                        System.out.println("Client with such name and surname already exists!");
                        throw new IllegalStateException();
                    }

                    System.out.print("Do you want to continue setting up? (y/n): ");
                    String answer = reader.readLine();

                    NameBuilder clientBuilder = clientInfo.bank().getClientBuilder();

                    if (answer.equals("y")) {
                        System.out.print("Client's passport data: ");
                        String clientPassportData = reader.readLine();

                        System.out.print("Client's address: ");
                        String clientAddress = reader.readLine();

                        clientBuilder.withName(clientInfo.name()).
                                withSurname(clientInfo.surname()).
                                withPassportData(clientPassportData).
                                withAddress(clientAddress).build();
                    } else {
                        clientBuilder.withName(clientInfo.name()).withSurname(clientInfo.surname()).build();
                    }

                    System.out.println("Client '" + clientInfo.name() +
                            " " + clientInfo.surname() + "' has been successfully created");

                } catch (IllegalStateException ignored) {
                }
            }

            case "/verify" -> {
                try {
                    Optional<Client> foundClient = ClientInfoRequester.requestClient(reader, selector.getContext());

                    if (foundClient.isEmpty()) {
                        System.out.println("There is no such client in this bank!");
                        throw new IllegalStateException();
                    }

                    if (foundClient.get().getClass() != UnverifiedClient.class) {
                        System.out.println("Client is already verified!");
                        throw new IllegalStateException();
                    }

                    System.out.print("Client's passport data: ");
                    String clientPassportData = reader.readLine();

                    System.out.print("Client's address: ");
                    String clientAddress = reader.readLine();

                    ((UnverifiedClient) foundClient.get()).verify(clientPassportData, clientAddress);
                    System.out.println("Client '" + foundClient.get().getName() +
                            " " + foundClient.get().getSurname() + "' has been successfully verified");

                } catch (IllegalStateException ignored) {
                }
            }

            default -> System.out.println(Handler.NO_SUCH_COMMAND_MESSAGE);
        }
    }
}
