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
        String req = "SELECT * FROM companies WHERE id=" + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(req + id);

            Set<Company> companies = new HashSet<>();
            while (resultSet.next()) {
                Company company = extractCompanyFromResultSet(resultSet);
                companies.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Company> findAll() {
        String req = "SELECT * FROM companies";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(req);

            List<Company> companies = new ArrayList<>();
            while (resultSet.next()) {
                Company company = extractCompanyFromResultSet(resultSet);
                companies.add(company);
            }
            return companies;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
    public void update(Company company) {
        String req = "UPDATE developers.companies SET name=?, count-of-employee=?, projects=? WHERE id=?";
        try {
            insertUpdate(company, req);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Statement statement = connection.createStatement();
            int i = statement.executeUpdate("DELETE FROM companies WHERE id=" + id);

            if (i == 1) {
                System.out.println("Companie with id " + id + " deleted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Company extractCompanyFromResultSet(ResultSet rs) throws SQLException {
        Company company = new Company();

        company.setId(rs.getLong("id"));
        company.setName(rs.getString("name"));
        company.setProjects((Set<Project>) rs.getObject("projects"));
        company.setCountOfEmployee(rs.getInt("count-of-employee"));
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
