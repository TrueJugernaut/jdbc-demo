package dao;

import model.Developer;
import model.Project;

import java.util.Set;

public interface ProjectDao extends CruidDao<Project>{
    @Override
    Project create(Project odject);

    @Override
    Project update(Project object);

    @Override
    void deleteById(Long id);
}
