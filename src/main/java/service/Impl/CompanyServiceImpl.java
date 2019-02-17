package service.Impl;

import dao.CompanyDao;
import model.Company;
import service.CompanyService;

public class CompanyServiceImpl implements CompanyService {

    CompanyDao companyDao;

    public CompanyServiceImpl(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @Override
    public Company findById(Long id) {
        return null;
    }

    @Override
    public void insert(Company company) {

    }

    @Override
    public void update(Company company) {

    }

    @Override
    public void delete(Long id) {

    }
}
