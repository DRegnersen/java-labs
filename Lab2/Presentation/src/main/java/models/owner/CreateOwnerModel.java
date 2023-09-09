package models.owner;

import java.time.LocalDate;

public record CreateOwnerModel(String name, LocalDate birthdate) {
}
