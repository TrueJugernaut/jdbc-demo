package dao.Impl;

import dao.AbstractDao;
import dao.CompanyDao;
import model.Company;
import model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class CompanyDaoImpl extends AbstractDao implements CompanyDao {

    public CompanyDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Set findAll() {
        String req = "SELECT * FROM companies";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(req);

            Set<Company> companies = new HashSet<>();
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
    public void insert(Company company) {
        String req = "INSERT into developers.developer VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";
        try {
            insertUpdate(company, req);
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
