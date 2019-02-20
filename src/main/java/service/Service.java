package service;

import java.util.List;

public interface Service<T> {
    T findById(Long id);

    List<T> findAll();

    void insert(T t);

    void update(T t, Long id);

    void delete(Long id);

}
