package com.dregnersen.presentation.models.cat;

import java.time.LocalDate;

public record CreateCatModel(String name, String color, String breed, LocalDate birthdate) {
}
