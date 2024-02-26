package com.dregnersen.presentation.controllers;

import com.dregnersen.application.dto.OwnerDto;
import com.dregnersen.application.services.owner.OwnerService;
import com.dregnersen.dataaccess.filtering.Filter;
import com.dregnersen.dataaccess.filtering.FilteringType;
import com.dregnersen.presentation.models.owner.AddCatModel;
import com.dregnersen.presentation.models.owner.CreateOwnerModel;
import com.dregnersen.presentation.models.owner.UpdateOwnerModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/owner")
@SecurityRequirement(name = "basicAuth")
public class OwnerController {
    private final OwnerService service;

    public OwnerController(OwnerService ownerService) {
        this.service = ownerService;
    }

    @PostMapping("/create")
    public OwnerDto createOwner(@RequestBody CreateOwnerModel model) {
        return service.createOwner(model.name(), model.birthdate());
    }

    @GetMapping
    public OwnerDto getOwner(@RequestParam(value = "id") Long id) {
        return service.getOwnerById(id);
    }

    @GetMapping("/find")
    public OwnerDto[] getOwnersByFilter(@RequestParam(value = "name", required = false) String name) {
        return service.findOwnersByFilter(manageFilters(name, FilteringType.FIND));
    }

    @GetMapping("/like")
    public OwnerDto[] getSimilarOwnersByFilter(@RequestParam(value = "name", required = false) String name) {
        return service.findOwnersByFilter(manageFilters(name, FilteringType.LIKE));
    }

    @PutMapping("/{id}/update")
    public OwnerDto updateOwner(@PathVariable("id") Long id, @RequestBody UpdateOwnerModel model) {
        service.updateOwner(id, model.newName());
        return service.getOwnerById(id);
    }

    @PutMapping("/add-cat")
    public OwnerDto addCat(@RequestBody AddCatModel model) {
        service.addCat(model.id(), model.catId());
        return service.getOwnerById(model.id());
    }

    @DeleteMapping("/{id}/remove")
    public void removeOwner(@PathVariable("id") Long id) {
        service.removeOwner(id);
    }

    private static List<Filter> manageFilters(String name, FilteringType filteringType) {
        List<Filter> filters = new ArrayList<>();

        if (name != null) {
            filters.add(new Filter("name", name, filteringType));
        }

        return filters;
    }
}
