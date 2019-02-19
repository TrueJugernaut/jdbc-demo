package dao;

import model.Customer;

import java.util.List;
import java.util.Set;

public interface CustomerDao extends CruidDao<Customer> {
    @Override
    Customer findById(Long id);

    @Override
    List<Customer> findAll();

    @Override
    void insert(Customer customer);

    @Override
    void update(Customer customer);

    @Override
    void deleteById(Long id);
}
