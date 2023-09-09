package com.dregnersen.security.application.services.user.impl;

import com.dregnersen.dataaccess.repositories.OwnerRepository;
import com.dregnersen.security.application.dto.DetailedUserDto;
import com.dregnersen.security.application.dto.UserDto;
import com.dregnersen.security.application.mappers.UserMapper;
import com.dregnersen.security.application.services.user.UserService;
import com.dregnersen.security.application.services.user.UserServiceSettings;
import com.dregnersen.security.dataaccess.Role;
import com.dregnersen.security.dataaccess.entities.User;
import com.dregnersen.security.dataaccess.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceSettings settings;

    @Autowired
    public UserServiceImpl(UserRepository repository,
                           OwnerRepository ownerRepository,
                           PasswordEncoder passwordEncoder,
                           UserServiceSettings userServiceSettings) {
        this.repository = repository;
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
        this.settings = userServiceSettings;
    }

    @Override
    public DetailedUserDto createUser(String login, String password, Long ownerId) {
        var userWithSameLogin = repository.findByLogin(login);
        if (userWithSameLogin.isPresent())
            throw new IllegalArgumentException();

        var owner = ownerRepository.findById(ownerId).orElseThrow(IllegalArgumentException::new);

        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        user.setOwner(owner);

        repository.save(user);
        return UserMapper.map(user).withName(owner.getName());
    }

    @Override
    public DetailedUserDto getUser(Long id) {
        var user = repository.findById(id).orElseThrow(IllegalArgumentException::new);
        return UserMapper.map(user).withName(user.getOwner().getName());
    }

    @Override
    public UserDto updateUser(Long id, String newLogin, String newPassword, Long newOwnerId) {
        var user = repository.findById(id).orElseThrow(IllegalArgumentException::new);

        if (newLogin != null)
            user.setLogin(newLogin);

        if (newPassword != null)
            user.setPassword(passwordEncoder.encode(newPassword));

        if (newOwnerId != null) {
            var owner = ownerRepository.findById(newOwnerId).orElseThrow(IllegalArgumentException::new);
            user.setOwner(owner);
        }

        repository.save(user);
        return UserMapper.map(user).withBasicsOnly();
    }

    @Override
    public void removeUser(Long id) {
        var user = repository.findById(id).orElseThrow(IllegalArgumentException::new);
        repository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return repository.findByLogin(login).orElseThrow(IllegalArgumentException::new);
    }

    @PostConstruct
    public void addInitialAdmin() {
        if (repository.findByLogin(settings.initialAdminLogin()).isEmpty()) {
            User defaultAdmin = new User();
            defaultAdmin.setLogin(settings.initialAdminLogin());
            defaultAdmin.setPassword(passwordEncoder.encode(settings.initialAdminPassword()));
            defaultAdmin.setRole(Role.ADMIN);

            repository.save(defaultAdmin);
        }
    }
}
