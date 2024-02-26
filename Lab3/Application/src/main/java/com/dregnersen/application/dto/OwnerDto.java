package com.dregnersen.application.dto;

import java.time.LocalDate;

public record OwnerDto(Long id, String name, LocalDate birthdate, Long[] cats) {
}
