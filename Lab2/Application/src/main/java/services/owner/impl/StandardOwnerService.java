package services.owner.impl;

import dao.cat.CatDao;
import dao.owner.OwnerDao;
import dto.OwnerDto;
import entities.Cat;
import entities.Owner;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import services.owner.OwnerService;
import tools.OwnerMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class StandardOwnerService implements OwnerService {
    @NonNull
    private OwnerDao<Owner, Long> dao;

    @NonNull
    private CatDao<Cat, Long> catDao;

    @Override
    public Long createOwner(String name, LocalDate birthdate) {
        Owner owner = new Owner();
        owner.setName(name);
        owner.setBirthdate(birthdate);
        owner.setCats(new ArrayList<>());
        return dao.create(owner);
    }

    @Override
    public OwnerDto getOwnerById(Long id) {
        return OwnerMapper.map(dao.findById(id).orElseThrow(NoSuchElementException::new));
    }

    @Override
    public void updateName(Long id, String newName) {
        Owner owner = dao.findById(id).orElseThrow(NoSuchElementException::new);
        owner.setName(newName);
        dao.update(owner);
    }

    @Override
    public void addCat(Long id, Long catId) {
        Owner owner = dao.findById(id).orElseThrow(NoSuchElementException::new);
        Cat cat = catDao.findById(catId).orElseThrow(NoSuchElementException::new);

        if (owner.getCats().stream().anyMatch(c -> c.getId().equals(catId))) {
            throw new IllegalArgumentException();
        }

        owner.addCat(cat);
        cat.setOwner(owner);

        dao.update(owner);
        catDao.update(cat);
    }

    @Override
    public void removeOwner(Long id) {
        dao.delete(dao.findById(id).orElseThrow(NoSuchElementException::new));
    }
}
