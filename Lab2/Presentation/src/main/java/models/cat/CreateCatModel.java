package models.cat;

import java.time.LocalDate;

public record CreateCatModel(String name, String breed, LocalDate birthdate) {
}
