package com.dregnersen.presentation.controllers;

import com.dregnersen.application.dto.CatDto;
import com.dregnersen.application.services.cat.CatService;
import com.dregnersen.dataaccess.filtering.FilteringType;
import com.dregnersen.presentation.models.cat.AddFriendModel;
import com.dregnersen.presentation.models.cat.CreateCatModel;
import com.dregnersen.presentation.models.cat.UpdateCatModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cat")
@SecurityRequirement(name = "basicAuth")
public class CatController extends CatFilteringController {
    private final CatService service;

    public CatController(CatService catService) {
        this.service = catService;
    }

    @PostMapping("/create")
    public CatDto createCat(@RequestBody CreateCatModel model) {
        return service.createCat(model.name(), model.color(), model.breed(), model.birthdate());
    }

    @GetMapping
    public CatDto getCat(@RequestParam(value = "id") Long id) {
        return service.getCatById(id);
    }

    @GetMapping("/find")
    public CatDto[] getCatsByFilter(@RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "color", required = false) String color,
                                    @RequestParam(value = "breed", required = false) String breed) {
        return service.findCatsByFilter(manageFilters(name, color, breed, FilteringType.FIND));
    }

    @GetMapping("/like")
    public CatDto[] getSimilarCatsByFilter(@RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "color", required = false) String color,
                                           @RequestParam(value = "breed", required = false) String breed) {
         return service.findCatsByFilter(manageFilters(name, color, breed, FilteringType.LIKE));
    }

    @PutMapping("/{id}/update")
    public CatDto updateCat(@PathVariable("id") Long id, @RequestBody UpdateCatModel model) {
        service.updateCat(id, model.newName(), model.newColor(), model.newBreed());
        return service.getCatById(id);
    }

    @PutMapping("/add-friend")
    public CatDto addFriend(@RequestBody AddFriendModel model) {
        service.addFriend(model.id(), model.friendId());
        return service.getCatById(model.id());
    }

    @DeleteMapping("/{id}/remove")
    public void removeCat(@PathVariable("id") Long id) {
        service.removeCat(id);
    }
}
