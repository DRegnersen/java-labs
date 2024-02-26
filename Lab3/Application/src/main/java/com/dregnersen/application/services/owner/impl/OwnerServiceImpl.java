package com.dregnersen.application.services.owner.impl;

import com.dregnersen.application.dto.OwnerDto;
import com.dregnersen.application.mappers.OwnerMapper;
import com.dregnersen.application.services.owner.OwnerService;
import com.dregnersen.dataaccess.entities.Cat;
import com.dregnersen.dataaccess.entities.Owner;
import com.dregnersen.dataaccess.filtering.Filter;
import com.dregnersen.dataaccess.filtering.specifications.impl.OwnerSpecification;
import com.dregnersen.dataaccess.repositories.CatRepository;
import com.dregnersen.dataaccess.repositories.OwnerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository repository;
    private final CatRepository catRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository, CatRepository catRepository) {
        this.repository = ownerRepository;
        this.catRepository = catRepository;
    }

    @Override
    public OwnerDto createOwner(String name, LocalDate birthdate) {
        Owner owner = new Owner();
        owner.setName(name);
        owner.setBirthdate(birthdate);
        owner.setCats(new ArrayList<>());
        repository.save(owner);
        return OwnerMapper.map(owner);
    }

    @Override
    public OwnerDto getOwnerById(Long id) {
        return OwnerMapper.map(repository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    @Override
    public OwnerDto[] findOwnersByFilter(List<Filter> filters) {
        return OwnerMapper.map(repository.findAll(new OwnerSpecification(filters)));
    }

    @Override
    public void updateOwner(Long id, String newName) {
        Owner owner = repository.findById(id).orElseThrow(NoSuchElementException::new);

        if (newName != null) {
            owner.setName(newName);
        }

        repository.save(owner);
    }

    @Override
    public void addCat(Long id, Long catId) {
        Owner owner = repository.findById(id).orElseThrow(NoSuchElementException::new);
        Cat cat = catRepository.findById(id).orElseThrow(NoSuchElementException::new);

        if (owner.getCats().stream().anyMatch(c -> c.getId().equals(catId))) {
            throw new IllegalArgumentException();
        }

        owner.addCat(cat);
        cat.setOwner(owner);

        repository.save(owner);
        catRepository.save(cat);
    }

    @Override
    public void removeOwner(Long id) {
        repository.delete(repository.findById(id).orElseThrow(NoSuchElementException::new));
    }
}
