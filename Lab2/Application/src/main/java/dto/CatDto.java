package dto;

import java.time.LocalDate;

public record CatDto(Long id, String name, String breed, LocalDate birthdate, Long[] friends) {
}
