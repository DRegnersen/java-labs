package com.dregnersen.security.application.mappers;

import com.dregnersen.security.dataaccess.entities.User;

public final class UserMapper {
    private UserMapper() {
    }

    public static UserMappingResult map(User user){
        return new UserMappingResult(user.getId(), user.getLogin(), user.getOwner().getId());
    }
}
