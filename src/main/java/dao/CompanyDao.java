package dao;

import model.Company;
import model.Developer;

import java.util.Set;

public interface CompanyDao {
    Set findAll();

    Company findOne(Long id);

    void create(Company company);

    void update(Company company);

    void deleteById(Long id);
}
