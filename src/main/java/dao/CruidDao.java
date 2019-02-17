package dao;

import java.util.Set;

public interface CruidDao<T> {
    T findById(Long id);

    Set<T> findAll();

    void insert(T t);

    void update(T t);

    void deleteById(Long id);
}
