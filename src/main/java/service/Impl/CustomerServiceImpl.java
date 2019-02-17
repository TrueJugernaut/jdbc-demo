package service.Impl;

import dao.CustomerDao;
import model.Customer;
import service.CustomerService;

public class CustomerServiceImpl implements CustomerService {

    CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Customer findById(Long id) {
        return null;
    }

    @Override
    public void insert(Customer customer) {

    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public void delete(Long id) {

    }
}
