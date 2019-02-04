package dao;

import model.Customer;
import model.Developer;

import java.util.Set;

public interface CustomerDao {
    Set findAll();

    Customer findOne(Long id);

    void create(Customer customer);

    void update(Customer customer);

    void deleteById(Long id);
}
