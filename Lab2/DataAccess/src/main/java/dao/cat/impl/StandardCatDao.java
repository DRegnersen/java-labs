package dao.cat.impl;

import dao.cat.CatDao;
import entities.Cat;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class StandardCatDao implements CatDao<Cat, Long> {
    @NonNull
    private EntityManager manager;

    @Override
    public Long create(Cat cat) {
        manager.getTransaction().begin();
        manager.detach(cat);
        manager.persist(cat);
        manager.getTransaction().commit();
        return cat.getId();
    }

    @Override
    public Optional<Cat> findById(Long id) {
        return Optional.ofNullable(manager.find(Cat.class, id));
    }

    @Override
    public void update(Cat cat) {
        manager.getTransaction().begin();
        manager.detach(cat);
        manager.merge(cat);
        manager.getTransaction().commit();
    }

    @Override
    public void delete(Cat cat) {
        manager.getTransaction();
        manager.remove(cat);
        manager.getTransaction().commit();
    }
}
