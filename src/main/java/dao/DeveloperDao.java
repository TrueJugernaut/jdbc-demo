package dao;

import model.Developer;

import java.util.Set;

public interface DeveloperDao extends CruidDao<Developer> {
    @Override
    Developer findById(Long id);

    @Override
    Set<Developer> findAll();

    @Override
    void insert(Developer object);

    @Override
    void update(Developer object);

    @Override
    void deleteById(Long id);
}
