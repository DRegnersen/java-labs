package com.dregnersen.banks.models;

import com.dregnersen.banks.entities.banks.Bank;
import org.jetbrains.annotations.NotNull;

/**
 * The type Client info.
 */
public record ClientInfo(@NotNull Bank bank, @NotNull String name, @NotNull String surname) {
}
