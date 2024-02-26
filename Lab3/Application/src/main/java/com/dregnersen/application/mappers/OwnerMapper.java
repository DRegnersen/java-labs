package com.dregnersen.application.mappers;

import com.dregnersen.application.dto.OwnerDto;
import com.dregnersen.dataaccess.entities.Cat;
import com.dregnersen.dataaccess.entities.Owner;

import java.util.List;

public final class OwnerMapper {
    public OwnerMapper() {
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

    public static OwnerDto[] map(List<Owner> owners){
        return (owners != null) ?
                owners.stream().map(OwnerMapper::map).toArray(OwnerDto[]::new) :
                new OwnerDto[0];
    }
}
