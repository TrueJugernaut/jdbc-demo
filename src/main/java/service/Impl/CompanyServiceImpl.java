package service.Impl;

import dao.CompanyDao;
import model.Company;
import service.CompanyService;

public class CompanyServiceImpl implements CompanyService {

    private CompanyDao companyDao;

    public CompanyServiceImpl(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @Override
    public Company findById(Long id) {
        return null;
    }

    @Override
    public void insert(Company company) {
        companyDao.insert(company);
    }

    @Override
    public void update(Company company) {

    }

    @Override
    public void delete(Long id) {

    }
}
