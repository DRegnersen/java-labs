package com.dregnersen.security.application.dto;

public record DetailedUserDto(Long id, String login, Long ownerId, String name) {
}
