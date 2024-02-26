package com.dregnersen.application.mappers;

import com.dregnersen.application.dto.CatDto;
import com.dregnersen.dataaccess.entities.Cat;

import java.util.List;

public final class CatMapper {
    public CatMapper() {
    }

    public static CatDto map(Cat cat) {
        return new CatDto(
                cat.getId(),
                cat.getName(),
                cat.getColor(),
                cat.getBreed(),
                cat.getBirthdate(),
                (cat.getOwner() != null) ? cat.getOwner().getId() : 0,
                (cat.getFriends() != null) ?
                        cat.getFriends().stream().map(Cat::getId).toArray(Long[]::new) :
                        new Long[0]);
    }

    public static CatDto[] map(List<Cat> cats) {
        return (cats != null) ?
                cats.stream().map(CatMapper::map).toArray(CatDto[]::new) :
                new CatDto[0];
    }
}
