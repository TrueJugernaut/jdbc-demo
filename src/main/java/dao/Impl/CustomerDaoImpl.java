package dao.Impl;

import dao.AbstractDao;
import dao.CustomerDao;
import model.Customer;
import model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl extends AbstractDao implements CustomerDao {

    public CustomerDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Customer findById(Long id) {
        final String FIND_CUSTOMER_BY_ID = "SELECT * FROM customers WHERE id=" + id;
        final String FIND_ALL_PROJECTS = "SELECT * FROM projects WHERE customer_id=";
        Customer customer = null;
        List<Project> projects = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(FIND_CUSTOMER_BY_ID);
            if (resultSet.next()) {
                customer.setName(resultSet.getString("name"));
                customer.setRegion("region");
            }

            resultSet = statement.executeQuery(FIND_ALL_PROJECTS + id);
            while (resultSet.next()) {
                projects.add(getProject(resultSet));
            }
            customer.setProjects(projects);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public void insert(Customer customer) {
        final String INSERT_CUSTOMER = "INSERT INTO customers(name, region) values(?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_CUSTOMER);
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getRegion());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Customer customer, Long id) {

    }

    @Override
    public void deleteById(Long id) {

    }

    Project getProject(ResultSet resultSet) throws SQLException {
        Project project = Project.builder()
                .name(resultSet.getString("name"))
                .id(resultSet.getLong("id"))
                .build();
        return null;
    }
}
