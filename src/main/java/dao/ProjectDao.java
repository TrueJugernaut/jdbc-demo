package dao;

import model.Developer;
import model.Project;

import java.util.List;
import java.util.Set;

public interface ProjectDao extends CruidDao<Project>{

    void addDeveloper(Project project, Developer developer);

    void deleteDeveloper(Project project);

    void setProjectCoast(Project project);

    Double getProjectCoast(Long id);

}
