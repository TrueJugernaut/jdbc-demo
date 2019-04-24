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
        final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM customers WHERE id=" + id;
        final String SELECT_ALL_PROJECTS_BY_ID = "SELECT * FROM projects WHERE customer_id=" + id;
        Customer customer = new Customer();
        List<Project> projects = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_CUSTOMER_BY_ID);
            if (resultSet.next()) {
                customer.setName(resultSet.getString("name"));
                customer.setRegion("region");
            }
            resultSet = statement.executeQuery(SELECT_ALL_PROJECTS_BY_ID);
            while (resultSet.next()) {
                projects.add(getProject(resultSet));
            }
            customer.setProjects(projects);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e1) {
            System.out.println("Oop`s something wrong - null pointer");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customers";

        List<Customer> customers = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_CUSTOMERS);
            while (resultSet.next()) {
                customers.add(getCustomer(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void insert(Customer customer) {
        final String INSERT_CUSTOMER = "INSERT INTO customers(name, region) values(?, ?)";
        customerInsert(customer, INSERT_CUSTOMER);
    }

    @Override
    public void update(Customer customer, Long id) {
        final String UPDATE_CUSTOMER = "UPDATE customers SET name=?, region=?, WHERE id=" + id;
        customerInsert(customer, UPDATE_CUSTOMER);
    }

    @Override
    public void deleteById(Long id) {
        final String DELETE_CUSTOMER = "DELETE FROM customers WHERE id=" + id;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE_CUSTOMER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Project getProject(ResultSet resultSet) throws SQLException {
        return Project.builder()
                .name(resultSet.getString("name"))
                .id(resultSet.getLong("id"))
                .build();
    }

    void customerInsert(Customer customer, String query) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getRegion());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e1) {
            System.out.println("Oop`s something wrong - null pointer");
        }
    }

    private Customer getCustomer(ResultSet resultSet) throws SQLException {
        return Customer.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .region(resultSet.getString("region"))
                .build();
    }
}
