package service.Impl;

import dao.ProjectDao;
import model.Developer;
import model.Project;
import service.ProjectService;

public class ProjectServiceImpl implements ProjectService {

    ProjectDao projectDao;

    public ProjectServiceImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public Project findById(Long id) {
        return null;
    }

    @Override
    public void insert(Project project) {

    }

    @Override
    public void update(Project project) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void addDeveloper(Project project, Developer developer) {
        projectDao.addDeveloper(project, developer);
    }
}
