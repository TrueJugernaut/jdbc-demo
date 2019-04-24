package service.Impl;

import dao.ProjectDao;
import model.Developer;
import model.Project;
import service.ProjectService;

import java.util.List;

public class ProjectServiceImpl implements ProjectService {

    ProjectDao projectDao;

    public ProjectServiceImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public Project findById(Long id) {
        return projectDao.findById(id);
    }

    @Override
    public List<Project> findAll() {
        return projectDao.findAll();
    }

    @Override
    public void insert(Project project) {
        projectDao.insert(project);
    }

    @Override
    public void update(Project project, Long id) {
        projectDao.update(project, id);

    }

    @Override
    public void delete(Long id) {
        projectDao.deleteById(id);
    }

    @Override
    public void addDeveloper(Project project, Developer developer) {
        projectDao.addDeveloper(project, developer);
    }

    @Override
    public void deleteDeveloper(Project project) {
        projectDao.deleteDeveloper(project);
    }

    @Override
    public void setProjectCoast(Project project) {
        projectDao.setProjectCoast(project);
    }

    @Override
    public Double getProjectCoast(Long id) {
        return projectDao.getProjectCoast(id);
    }
}
