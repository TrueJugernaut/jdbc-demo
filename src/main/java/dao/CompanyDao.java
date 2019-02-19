package dao;

import model.Company;

import java.util.List;

public interface CompanyDao extends CruidDao<Company> {
    @Override
    Company findById(Long id);

    @Override
    List<Company> findAll();

    @Override
    void insert(Company company);

    @Override
    void update(Company company);

    @Override
    void deleteById(Long id);
}
