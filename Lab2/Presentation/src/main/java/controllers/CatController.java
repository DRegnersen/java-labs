package controllers;

import dto.CatDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import models.cat.AddFriendModel;
import models.cat.CreateCatModel;
import models.cat.UpdateCatNameModel;
import services.cat.CatService;

@RequiredArgsConstructor
public class CatController {
    @NonNull
    private CatService service;

    public Long createCat(@NonNull CreateCatModel model) {
        return service.createCat(model.name(), model.breed(), model.birthdate());
    }

    public CatDto getCat(@NonNull Long id) {
        return service.getCatById(id);
    }

    public void updateName(@NonNull UpdateCatNameModel model) {
        service.updateName(model.id(), model.newName());
    }

    public void addFriend(@NonNull AddFriendModel model) {
        service.addFriend(model.id(), model.friendId());
    }

    public void removeCat(@NonNull Long id) {
        service.removeCat(id);
    }
}
