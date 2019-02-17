package service;

import model.Developer;
import model.Project;

public interface ProjectService extends Service<Project> {
    public void addDeveloper(Project project, Developer developer);
}
