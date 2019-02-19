package dao.Impl;

import dao.AbstractDao;
import dao.CustomerDao;
import model.Customer;

import java.sql.Connection;
import java.util.List;

public class CustomerDaoImpl extends AbstractDao implements CustomerDao {

    public CustomerDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Customer findById(Long id) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public void insert(Customer customer) {

    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
