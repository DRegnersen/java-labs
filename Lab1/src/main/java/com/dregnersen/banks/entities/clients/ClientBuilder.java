package com.dregnersen.banks.entities.clients;

import com.dregnersen.banks.entities.banks.ModifiableBank;
import com.dregnersen.banks.entities.clients.builder.*;
import com.dregnersen.banks.entities.clients.impl.VerifiedClient;
import com.dregnersen.banks.entities.clients.impl.UnverifiedClient;
import com.dregnersen.banks.entities.notifiers.Notifier;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * The type Client builder.
 */
public class ClientBuilder implements IdBuilder, BankBuilder, NameBuilder, SurnameBuilder, ExtraDataBuilder {
    private UUID id;
    private ModifiableBank bank;
    private String name;
    private String surname;
    private String passportData;
    private String address;

    @Override
    public BankBuilder withId(@NotNull UUID id) {
        this.id = id;
        return this;
    }

    @Override
    public NameBuilder withBank(@NotNull ModifiableBank bank) {
        this.bank = bank;
        return this;
    }

    @Override
    public SurnameBuilder withName(@NotNull String name) {
        this.name = name;
        return this;
    }

    @Override
    public ExtraDataBuilder withSurname(@NotNull String surname) {
        this.surname = surname;
        return this;
    }

    @Override
    public ExtraDataBuilder withPassportData(@NotNull String passportData) {
        this.passportData = passportData;
        return this;
    }

    @Override
    public ExtraDataBuilder withAddress(@NotNull String address) {
        this.address = address;
        return this;
    }

    @Override
    public Client build() {
        if (id == null || bank == null || name == null || surname == null) {
            throw new IllegalStateException();
        }

        Client client = (passportData == null || address == null) ?
                new UnverifiedClient(id, bank, name, surname, new Notifier()) :
                new VerifiedClient(id, bank, name, surname, passportData, address, new Notifier());

        bank.addClient(client);
        return client;
    }
}
