package service;

import model.Developer;
import model.Project;

public interface ProjectService extends Service<Project> {

    void addDeveloper(Project project, Developer developer);

    void deleteDeveloper(Project project);

    void setProjectCoast(Project project);

    Double getProjectCoast(Long id);
}
