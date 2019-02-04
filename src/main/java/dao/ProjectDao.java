package dao;

import model.Developer;
import model.Project;

import java.util.Set;

public interface ProjectDao {
    Set findAll();

    Project findOne(Long id);

    void create(Project project);

    void update(Project project);

    void deleteById(Long id);
}
