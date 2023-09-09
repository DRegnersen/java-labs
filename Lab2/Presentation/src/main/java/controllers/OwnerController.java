package controllers;

import dto.OwnerDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import models.owner.AddCatModel;
import models.owner.CreateOwnerModel;
import models.owner.UpdateOwnerNameModel;
import services.owner.OwnerService;

@RequiredArgsConstructor
public class OwnerController {
    @NonNull
    private OwnerService service;

    public Long createOwner(@NonNull CreateOwnerModel model) {
        return service.createOwner(model.name(), model.birthdate());
    }

    public OwnerDto getOwner(@NonNull Long id) {
        return service.getOwnerById(id);
    }

    public void updateName(@NonNull UpdateOwnerNameModel model) {
        service.updateName(model.id(), model.newName());
    }

    public void addCat(@NonNull AddCatModel model) {
        service.addCat(model.id(), model.catId());
    }

    public void removeOwner(@NonNull Long id) {
        service.removeOwner(id);
    }
}
