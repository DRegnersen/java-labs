package dao.owner;

import java.io.Serializable;
import java.util.Optional;

public interface OwnerDao<TEntity, TId extends Serializable> {
    TId create(TEntity entity);

    Optional<TEntity> findById(TId id);

    void update(TEntity entity);

    void delete(TEntity entity);
}
