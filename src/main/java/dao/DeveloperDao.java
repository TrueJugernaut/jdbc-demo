package dao;

import model.Developer;

import java.util.Set;

public interface DeveloperDao extends CruidDao<Developer> {
    @Override
    Developer findById(Long id);

    @Override
    Set<Developer> findAll();

    @Override
    void insert(Developer developer);

    @Override
    void update(Developer developer);

    @Override
    void deleteById(Long id);
}
