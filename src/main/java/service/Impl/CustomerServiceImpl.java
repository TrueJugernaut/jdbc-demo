package service.Impl;

import dao.CustomerDao;
import model.Customer;
import service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Customer findById(Long id) {
        return customerDao.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public void insert(Customer customer) {
        customerDao.insert(customer);
    }

    @Override
    public void update(Customer customer, Long id) {
        customerDao.update(customer, id);
    }

    @Override
    public void delete(Long id) {
        customerDao.deleteById(id);
    }
}
