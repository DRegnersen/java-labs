package services.cat.impl;

import dao.cat.CatDao;
import dto.CatDto;
import entities.Cat;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import services.cat.CatService;
import tools.CatMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class StandardCatService implements CatService {
    @NonNull
    private CatDao<Cat, Long> dao;

    @Override
    public Long createCat(String name, String breed, LocalDate birthdate) {
        Cat cat = new Cat();
        cat.setName(name);
        cat.setBreed(breed);
        cat.setBirthdate(birthdate);
        cat.setFriends(new ArrayList<>());
        return dao.create(cat);
    }

    @Override
    public CatDto getCatById(Long id) {
        return CatMapper.map(dao.findById(id).orElseThrow(NoSuchElementException::new));
    }

    @Override
    public void updateName(Long id, String newName) {
        Cat cat = dao.findById(id).orElseThrow(NoSuchElementException::new);
        cat.setName(newName);
        dao.update(cat);
    }

    @Override
    public void addFriend(Long id, Long friendId) {
        if (id.equals(friendId)) {
            throw new IllegalArgumentException();
        }

        Cat cat = dao.findById(id).orElseThrow(NoSuchElementException::new);
        Cat friend = dao.findById(friendId).orElseThrow(NoSuchElementException::new);

        if (cat.getFriends().stream().anyMatch(f -> f.getId().equals(friendId))) {
            throw new IllegalArgumentException();
        }

        cat.addFriend(friend);
        dao.update(cat);
    }

    @Override
    public void removeCat(Long id) {
        dao.delete(dao.findById(id).orElseThrow(NoSuchElementException::new));
    }
}
