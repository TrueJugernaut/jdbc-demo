package dao.Impl;

import dao.AbstractDao;
import dao.CustomerDao;
import dao.DeveloperDao;
import dao.ProjectDao;
import model.Customer;
import model.Developer;
import model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDaoImpl extends AbstractDao implements ProjectDao {

    public ProjectDaoImpl(Connection connection) {
        super(connection);
    }

    private DeveloperDao developerDao = new DeveloperDaoImpl(connection);

    private CustomerDao customerDao = new CustomerDaoImpl(connection);

    @Override
    public Project findById(Long id) {
        final String SELECT_PROJECT_BY_ID = "SELECT * FROM projects WHERE id=" + id;
        final String SELECT_CUSTOMERS_FOR_PROJECT = "SELECT * FROM customers WHERE id=";
        final String SELECT_ALL_DEVELOPERS_FOR_PROJECT = "";

        Project project = new Project();
        Customer customer = new Customer();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_PROJECT_BY_ID);
            if (resultSet.next()) project = getProject(resultSet);

            //Select all customers
            resultSet = statement.executeQuery(SELECT_CUSTOMERS_FOR_PROJECT + project.getId());
            if (resultSet.next()) project.setCustomer(getCustomer(resultSet));


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    @Override
    public List<Project> findAll() {
        final String SELECT_ALL_PROJECTS = "SELECT * FROM projects";

        List<Project> projects = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_PROJECTS);
            while (resultSet.next()) {
                projects.add(getProject(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public void insert(Project project) {
        final String INSERT_PROJECT = "INSERT into projects(name, coast, customer_id) VALUES (?, ?, ?)";

        final String SELECT_LAST_PROJECT_INDEX = "SELECT MAX(id) AS project_id FROM projects";

        final String INSERT_IN_DEVELOPERS_PROJECTS = "INSERT INTO developers_projects (project_id, developer_id) values(?, ?)";

        List<Developer> developers = project.getDevelopers();

        try {
            Statement statement = connection.createStatement();

            //insert customer
            customerDao.insert(project.getCustomer());

            //insert all developers
            ResultSet resultSet = statement.executeQuery(SELECT_LAST_PROJECT_INDEX);
            resultSet.next();
            long projectId = resultSet.getLong("project_id");

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_IN_DEVELOPERS_PROJECTS);
            for (Developer developer : developers) {
                preparedStatement.setLong(1, projectId);
                preparedStatement.setLong(1, developer.getId());
                preparedStatement.execute();
            }
            //Insert project
            insertUpdate(project, INSERT_PROJECT);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(Project project, Long id) {
        final String UPDATE_PROJECT = "UPDATE projects SET name=?, coast=?, customer_id=?" + id;
        try {
            insertUpdate(project, UPDATE_PROJECT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        final String DELETE_PROJECT = "DELETE FROM projects WHERE id=" + id;

        final String DELETE_PROJECTS_DEVELOPERS = "DELETE FROM developers_projects where project_id=" + id;

        try (Statement statement = connection.createStatement()) {

            //delete projects_developers
            statement.executeUpdate(DELETE_PROJECTS_DEVELOPERS);

            //delete projects
            statement.executeUpdate(DELETE_PROJECT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addDeveloper(Project project, Developer developer) {

    }

    @Override
    public void deleteDeveloper(Project project, Developer developer) {

    }

    @Override
    public void setProjectCoast(Project project) {

    }

    @Override
    public void getProjectCoast(Project project) {

    }

    private void insertUpdate(Project project, String INSERT_PROJECT) throws SQLException {

        final String SELECT_LAST_CUSTOMER_INDEX = "SELECT MAX(id) AS customer_id FROM customers";

        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PROJECT);

        ResultSet resultSet = preparedStatement.executeQuery(SELECT_LAST_CUSTOMER_INDEX);
        resultSet.next();
        long customer_id = resultSet.getLong("customer_id");

        try (PreparedStatement statement = connection.prepareStatement(INSERT_PROJECT)) {
            statement.setString(1, project.getName());
            statement.setDouble(2, project.getCoast());
            statement.setLong(3, customer_id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Project getProject(ResultSet resultSet) throws SQLException {
        return Project.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .coast(resultSet.getDouble("coast"))
                .build();

    }

    private Customer getCustomer(ResultSet resultSet) throws SQLException {
        return Customer.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .region(resultSet.getString("region"))
                .build();
    }

}
