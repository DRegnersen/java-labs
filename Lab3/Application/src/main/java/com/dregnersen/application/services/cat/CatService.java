package com.dregnersen.application.services.cat;

import com.dregnersen.application.dto.CatDto;
import com.dregnersen.dataaccess.filtering.Filter;

import java.time.LocalDate;
import java.util.List;

public interface CatService {
    CatDto createCat(String name, String color, String breed, LocalDate birthdate);

    CatDto getCatById(Long id);

    CatDto[] findCatsByFilter(List<Filter> filters);

    void updateCat(Long id, String newName, String newColor, String newBreed);

    void addFriend(Long id, Long friendId);

    void removeCat(Long id);
}
