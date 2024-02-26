package com.dregnersen.application.services.owner;

import com.dregnersen.application.dto.OwnerDto;
import com.dregnersen.dataaccess.filtering.Filter;

import java.time.LocalDate;
import java.util.List;

public interface OwnerService {
    OwnerDto createOwner(String name, LocalDate birthdate);

    OwnerDto getOwnerById(Long id);

    OwnerDto[] findOwnersByFilter(List<Filter> filters);

    void updateOwner(Long id, String newName);

    void addCat(Long id, Long catId);

    void removeOwner(Long id);
}
