package com.dregnersen.banks.mainapplication;

import com.dregnersen.banks.models.Money;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * The type Money requester.
 */
public final class MoneyRequester {
    private MoneyRequester() {
    }

    /**
     * Request optional.
     *
     * @param reader the reader
     * @return the optional
     * @throws IOException the io exception
     */
    public static Optional<Money> request(@NotNull BufferedReader reader) throws IOException {
        try {
            return Optional.of(new Money(BigDecimal.valueOf(Double.parseDouble(reader.readLine()))));
        } catch (NumberFormatException e) {
            System.out.println("Incorrect money format!");
        }

        return Optional.empty();
    }
}
