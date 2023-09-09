package com.dregnersen.security.application.services.user;

import com.dregnersen.security.application.dto.DetailedUserDto;
import com.dregnersen.security.application.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    DetailedUserDto createUser(String login, String password, Long ownerId);

    DetailedUserDto getUser(Long id);

    UserDto updateUser(Long id, String newLogin, String newPassword, Long newOwnerId);

    void removeUser(Long id);
}
