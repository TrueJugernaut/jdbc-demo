package dao;

import model.Developer;

import java.util.List;
import java.util.Set;

public interface DeveloperDao extends CruidDao<Developer> {
    @Override
    Developer findById(Long id);

    @Override
    List<Developer> findAll();

    @Override
    void insert(Developer developer);

    @Override
    void update(Developer developer);

    @Override
    void deleteById(Long id);
}
