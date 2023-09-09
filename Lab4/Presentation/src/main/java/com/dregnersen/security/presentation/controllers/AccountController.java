package com.dregnersen.security.presentation.controllers;

import com.dregnersen.application.dto.CatDto;
import com.dregnersen.application.services.cat.CatService;
import com.dregnersen.dataaccess.filtering.FilteringType;
import com.dregnersen.presentation.controllers.CatFilteringController;
import com.dregnersen.security.application.services.user.UserService;
import com.dregnersen.security.dataaccess.entities.User;
import com.dregnersen.security.presentation.models.account.ChangePasswordModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/account")
@SecurityRequirement(name = "basicAuth")
public class AccountController extends CatFilteringController {
    private final UserService userService;
    private final CatService catService;

    public AccountController(UserService userService, CatService catService) {
        this.userService = userService;
        this.catService = catService;
    }

    @PutMapping("change-password")
    public void changePassword(@AuthenticationPrincipal User user, @RequestBody ChangePasswordModel model) {
        userService.updateUser(user.getId(), null, model.newPassword(), null);
    }

    @GetMapping("get-cat")
    public CatDto getCat(@AuthenticationPrincipal User user, @RequestParam(value = "cat_id") Long catId) {
        var cat = catService.getCatById(catId);
        if (!cat.owner().equals(user.getOwner().getId()))
            throw new IllegalArgumentException();

        return cat;
    }

    @GetMapping("find-cat")
    public CatDto[] getCatsByFilter(@AuthenticationPrincipal User user,
                                    @RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "color", required = false) String color,
                                    @RequestParam(value = "breed", required = false) String breed) {
        var cats = catService.findCatsByFilter(manageFilters(name, color, breed, FilteringType.FIND));
        return Arrays.stream(cats).filter(c -> c.owner().equals(user.getOwner().getId())).toArray(CatDto[]::new);
    }

    @GetMapping("cat-like")
    public CatDto[] getSimilarCatsByFilter(@AuthenticationPrincipal User user,
                                           @RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "color", required = false) String color,
                                           @RequestParam(value = "breed", required = false) String breed) {
        var cats = catService.findCatsByFilter(manageFilters(name, color, breed, FilteringType.LIKE));
        return Arrays.stream(cats).filter(c -> c.owner().equals(user.getOwner().getId())).toArray(CatDto[]::new);
    }
}
