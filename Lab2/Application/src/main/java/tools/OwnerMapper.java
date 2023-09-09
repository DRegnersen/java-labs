package tools;

import dto.OwnerDto;
import entities.Cat;
import entities.Owner;

public final class OwnerMapper {
    private OwnerMapper() {
    }

    public static OwnerDto map(Owner owner) {
        return new OwnerDto(
                owner.getId(),
                owner.getName(),
                owner.getBirthdate(),
                (owner.getCats() != null) ?
                        owner.getCats().stream().map(Cat::getId).toArray(Long[]::new) :
                        new Long[0]);
    }
}
