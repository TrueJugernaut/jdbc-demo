package dao;

import java.util.Set;

public interface CruidDao<T> {
    T findById(Long id);

    Set<T> findAll();

    void insert(T object);

    void update(T object);

    void deleteById(Long id);
}
