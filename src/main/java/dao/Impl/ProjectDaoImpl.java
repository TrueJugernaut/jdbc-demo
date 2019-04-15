package dao.Impl;

import dao.AbstractDao;
import dao.ProjectDao;
import model.Developer;
import model.Project;

import java.sql.Connection;
import java.util.List;

public class ProjectDaoImpl extends AbstractDao implements ProjectDao {

    public ProjectDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Project findById(Long id) {
        return null;
    }

    @Override
    public List<Project> findAll() {
        return null;
    }

    @Override
    public void insert(Project project) {
        final String INSERT_PROJECT = "INSERT into projects(name, customer_id) VALUES (?, ?)";

        final String SELECT_LAST_PROJECT_INDEX = "SELECT MAX(id) AS id FROM projects";

        final String INSERT_CUSTOMER = "INSERT INTO customers(name, region) VALUES(?, ?)";

        final String INSERT_DEVELOPER_PROJECT =
                "INSERT INTO developers_projects(developer_id, project_id) VALUES (?, ?)";

    }

    @Override
    public void update(Project project, Long id) {

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
