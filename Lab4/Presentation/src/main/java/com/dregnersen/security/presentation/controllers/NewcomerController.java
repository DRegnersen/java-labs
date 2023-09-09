package com.dregnersen.security.presentation.controllers;

import com.dregnersen.security.application.dto.DetailedUserDto;
import com.dregnersen.security.application.services.user.UserService;
import com.dregnersen.security.presentation.models.newcomer.SignUpModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/newcomer")
@SecurityRequirement(name = "basicAuth")
public class NewcomerController {
    private final UserService service;

    public NewcomerController(UserService service) {
        this.service = service;
    }

    @PostMapping("sign-up")
    public DetailedUserDto signUp(@RequestBody SignUpModel model){
        return service.createUser(model.login(), model.password(), model.invitationId());
    }
}
