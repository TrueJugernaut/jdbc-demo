package dao;

import model.Developer;

import java.util.Set;

public interface DeveloperDao {
    Set findAll();

    Developer findOne(Long id);

    void create(Developer developer);

    void update(Developer developer);

    void deleteById(Long id);
}
