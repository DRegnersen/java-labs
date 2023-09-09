package dao.cat;

import java.io.Serializable;
import java.util.Optional;

public interface CatDao<TEntity, TId extends Serializable> {
    TId create(TEntity entity);

    Optional<TEntity> findById(TId id);

    void update(TEntity entity);

    void delete(TEntity entity);
}
