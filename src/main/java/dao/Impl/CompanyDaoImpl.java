package dao.Impl;

import dao.AbstractDao;
import dao.CompanyDao;
import model.Company;
import model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompanyDaoImpl extends AbstractDao implements CompanyDao {

    public CompanyDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Company findById(Long id) {
        final String SELECT_COMPANY_BY_ID = "SELECT * FROM companies WHERE id=" + id;

        Company company = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_COMPANY_BY_ID);
            if (!resultSet.next()) {
                return company;
            }
            company = getCompany(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return company;
    }

    @Override
    public List<Company> findAll() {
        final String SELECT_ALL = "SELECT * FROM Company";
        List<Company> companies = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            while (resultSet.next()) {
                Company company = getCompany(resultSet);
                companies.add(company);
            }
            return companies;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public void insert(Company company) {
        final String INSERT_COMPANY =
                "INSERT into company(name, countOfEmployee) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMPANY);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setInt(2, company.getCountOfEmployee());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Company company, Long id) {
        String req = "UPDATE company SET name=?, countOfEmployee=?, project_id=? WHERE id=";
        try {
            insertUpdate(company, req);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        final String DELET_ALL_COMPANIES = "DELETE FROM companies WHERE id=" + id;
        try {
            Statement statement = connection.createStatement();
            int i = statement.executeUpdate(DELET_ALL_COMPANIES);

            if (i == 1) {
                System.out.println("Companie with id " + id + " deleted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Company getCompany(ResultSet rs) throws SQLException {
        Company company = new Company();

        company.setId(rs.getLong("id"));
        company.setName(rs.getString("name"));
        company.setProjects((Set<Project>) rs.getObject("project_id"));
        company.setCountOfEmployee(rs.getInt("countOfEmployee"));
        return company;
    }

    private void insertUpdate(Company com, String req) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, com.getName());
        preparedStatement.setInt(2, com.getCountOfEmployee());
        preparedStatement.setObject(3, com.getProjects());

        int i = preparedStatement.executeUpdate();

        if (i == 1) {
            System.out.println("Company updated/created");
        }
    }
}
