package com.dregnersen.banks.mainapplication;

import com.dregnersen.banks.entities.banks.Bank;
import com.dregnersen.banks.entities.clients.Client;
import com.dregnersen.banks.models.ApplicationContext;
import com.dregnersen.banks.models.ClientInfo;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

/**
 * The type Client info requester.
 */
public final class ClientInfoRequester {
    private ClientInfoRequester() {
    }

    /**
     * Request info client info.
     *
     * @param reader  the reader
     * @param context the context
     * @return the client info
     * @throws IOException the io exception
     */
    public static ClientInfo requestInfo(@NotNull BufferedReader reader,
                                         @NotNull ApplicationContext context) throws IOException {
        System.out.print("Bank's name: ");
        String bankName = reader.readLine();

        Optional<Bank> foundBank = context.centralBank().getBanks().stream().
                filter(bank -> bank.getName().equals(bankName)).findFirst();

        if (foundBank.isEmpty()) {
            System.out.println("There is no bank with such name!");
            throw new IllegalStateException();
        }

        System.out.print("Client's name: ");
        String clientName = reader.readLine();

        System.out.print("Client's surname: ");
        String clientSurname = reader.readLine();

        return new ClientInfo(foundBank.get(), clientName, clientSurname);
    }

    /**
     * Request client optional.
     *
     * @param reader  the reader
     * @param context the context
     * @return the optional
     * @throws IOException the io exception
     */
    public static Optional<Client> requestClient(@NotNull BufferedReader reader,
                                                 @NotNull ApplicationContext context) throws IOException {
        ClientInfo clientInfo = requestInfo(reader, context);

        return clientInfo.bank().getClients().stream().
                filter(client -> client.getName().equals(clientInfo.name())).
                filter(client -> client.getSurname().equals(clientInfo.surname())).findFirst();
    }
}
