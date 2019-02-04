package dao;

import model.Customer;

import java.util.Set;

public interface CustomerDao extends CruidDao<Customer> {
    @Override
    Customer findById(Long id);

    @Override
    Set<Customer> findAll();

    @Override
    void insert(Customer customer);

    @Override
    void update(Customer customer);

    @Override
    void deleteById(Long id);
}
