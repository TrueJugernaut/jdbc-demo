package dao;

import model.Developer;
import model.Project;

import java.util.List;
import java.util.Set;

public interface ProjectDao extends CruidDao<Project>{
    @Override
    Project findById(Long id);

    @Override
    List<Project> findAll();

    @Override
    void insert(Project project);

    @Override
    void update(Project project);

    @Override
    void deleteById(Long id);

    void addDeveloper(Project project, Developer developer);

    void deleteDeveloper(Project project, Developer developer);
}
