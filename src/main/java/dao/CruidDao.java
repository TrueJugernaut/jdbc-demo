package dao;

import java.util.List;

public interface CruidDao<T> {
    T findById(Long id);

    List<T> findAll();

    void insert(T t);

    void update(T t, Long id);

    void deleteById(Long id);
}
