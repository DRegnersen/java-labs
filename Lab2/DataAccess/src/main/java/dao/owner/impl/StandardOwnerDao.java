package dao.owner.impl;

import dao.owner.OwnerDao;
import entities.Owner;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class StandardOwnerDao implements OwnerDao<Owner, Long> {
    @NonNull
    private EntityManager manager;

    @Override
    public Long create(Owner owner) {
        manager.getTransaction().begin();
        manager.detach(owner);
        manager.persist(owner);
        manager.getTransaction().commit();
        return owner.getId();
    }

    @Override
    public Optional<Owner> findById(Long id) {
        return Optional.ofNullable(manager.find(Owner.class, id));
    }

    @Override
    public void update(Owner owner) {
        manager.getTransaction().begin();
        manager.detach(owner);
        manager.merge(owner);
        manager.getTransaction().commit();
    }

    @Override
    public void delete(Owner owner) {
        manager.getTransaction().begin();
        manager.remove(owner);
        manager.getTransaction().commit();
    }
}
