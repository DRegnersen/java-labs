package com.dregnersen.security.application.mappers;

import com.dregnersen.security.application.dto.DetailedUserDto;
import com.dregnersen.security.application.dto.UserDto;

public record UserMappingResult(Long id, String login, Long ownerId) {
    public UserDto withBasicsOnly(){
        return new UserDto(id, login, ownerId);
    }

    public DetailedUserDto withName(String name){
        return new DetailedUserDto(id, login, ownerId, name);
    }
}
