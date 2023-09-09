package com.dregnersen.security.presentation.controllers;

import com.dregnersen.security.application.dto.DetailedUserDto;
import com.dregnersen.security.application.dto.UserDto;
import com.dregnersen.security.application.services.user.UserService;
import com.dregnersen.security.presentation.models.user.UpdateUserModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "basicAuth")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public DetailedUserDto getUser(@RequestParam(value = "id") Long id){
        return service.getUser(id);
    }

    @PutMapping("/{id}/update")
    public UserDto updateUser(@PathVariable("id") Long id, @RequestBody UpdateUserModel model){
        return service.updateUser(id, model.newLogin(), null, model.newOwnerId());
    }

    @DeleteMapping("/{id}/remove")
    public void removeUser(@PathVariable("id") Long id){
        service.removeUser(id);
    }
}
