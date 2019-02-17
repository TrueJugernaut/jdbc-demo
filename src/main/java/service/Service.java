package service;

public interface Service<T> {
    T findById(Long id);

    void insert(T t);

    void update(T t);

    void delete(Long id);

}
