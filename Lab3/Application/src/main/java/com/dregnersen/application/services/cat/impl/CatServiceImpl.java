package com.dregnersen.application.services.cat.impl;

import com.dregnersen.application.dto.CatDto;
import com.dregnersen.application.mappers.CatMapper;
import com.dregnersen.application.services.cat.CatService;
import com.dregnersen.dataaccess.entities.Cat;
import com.dregnersen.dataaccess.filtering.Filter;
import com.dregnersen.dataaccess.filtering.specifications.impl.CatSpecification;
import com.dregnersen.dataaccess.repositories.CatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CatServiceImpl implements CatService {
    private final CatRepository repository;

    public CatServiceImpl(CatRepository catRepository) {
        this.repository = catRepository;
    }

    @Override
    public CatDto createCat(String name, String color, String breed, LocalDate birthdate) {
        Cat cat = new Cat();
        cat.setName(name);
        cat.setColor(color);
        cat.setBreed(breed);
        cat.setBirthdate(birthdate);
        cat.setFriends(new ArrayList<>());
        repository.save(cat);
        return CatMapper.map(cat);
    }

    @Override
    public CatDto getCatById(Long id) {
        return CatMapper.map(repository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    @Override
    public CatDto[] findCatsByFilter(List<Filter> filters) {
        return CatMapper.map(repository.findAll(new CatSpecification(filters)));
    }

    @Override
    public void updateCat(Long id, String newName, String newColor, String newBreed) {
        Cat cat = repository.findById(id).orElseThrow(NoSuchElementException::new);

        if (newName != null) {
            cat.setName(newName);
        }
        if (newColor != null) {
            cat.setColor(newColor);
        }
        if (newBreed != null) {
            cat.setBreed(newBreed);
        }

        repository.save(cat);
    }

    @Override
    public void addFriend(Long id, Long friendId) {
        if (id.equals(friendId)) {
            throw new IllegalArgumentException();
        }

        Cat cat = repository.findById(id).orElseThrow(NoSuchElementException::new);
        Cat friend = repository.findById(id).orElseThrow(NoSuchElementException::new);

        if (cat.getFriends().stream().anyMatch(f -> f.getId().equals(friendId))) {
            throw new IllegalArgumentException();
        }

        cat.addFriend(friend);
        repository.save(cat);
    }

    @Override
    public void removeCat(Long id) {
        repository.delete(repository.findById(id).orElseThrow(NoSuchElementException::new));
    }
}
