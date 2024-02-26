package com.dregnersen.application.dto;

import java.time.LocalDate;

public record CatDto(Long id, String name, String color,  String breed, LocalDate birthdate, Long owner, Long[] friends) {
}
