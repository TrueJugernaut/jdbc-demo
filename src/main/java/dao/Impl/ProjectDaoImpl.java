package dao.Impl;

import dao.AbstractDao;
import dao.CruidDao;
import dao.ProjectDao;
import model.Developer;
import model.Project;

import java.sql.Connection;
import java.util.Set;

public class ProjectDaoImpl extends AbstractDao implements ProjectDao {

    public ProjectDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Project findById(Long id) {
        return null;
    }

    @Override
    public Set<Project> findAll() {
        return null;
    }

    @Override
    public void insert(Project project) {

    }

    @Override
    public void update(Project project) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void addDeveloper(Project project, Developer developer) {

    }

    @Override
    public void deleteDeveloper(Project project, Developer developer) {

    }
}
