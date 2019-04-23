package dao.Impl;

import dao.AbstractDao;
import dao.CompanyDao;
import model.Company;
import model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl extends AbstractDao implements CompanyDao {

    public CompanyDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Company findById(Long id) {
        final String SELECT_COMPANY_BY_ID = "SELECT * FROM companies WHERE id=" + id;
        final String SELECT_ALL_PROJECTS_BY_ID = "SELECT projects.id, projects.name\n" +
                "                FROM projects\n" +
                "                INNER JOIN companies_projects\n" +
                "                ON projects.id = companies_projects.project_id\n" +
                "                INNER JOIN companies\n" +
                "                ON companies_projects.company_id = companies.id\n" +
                "                WHERE company_id =" + id + ";";

        Company company = new Company();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_COMPANY_BY_ID);
            if (!resultSet.next()) {
                System.out.println("Company with id " + id + " doesn't exist: ");
                return null;
            }
            company = getCompany(resultSet);
            List<Project> projects = new ArrayList<>();
            resultSet = statement.executeQuery(SELECT_ALL_PROJECTS_BY_ID);
            while (resultSet.next()) {
                projects.add(Project.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .build());
            }
            company.setProjects(projects);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e1) {
            System.out.println("Object Company with id=" + id + " does not exist");
        }
        return company;
    }

    @Override
    public List<Company> findAll() {
        final String SELECT_ALL = "SELECT * FROM companies";

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
                "INSERT into companies(name, count_of_employee) VALUES (?, ?)";
        final String SELECT_LAST_COMPANY_INDEX = "SELECT MAX(id) AS company_id FROM companies";
        final String INSERT_COMPANIES_PROJECTS =
                "INSERT INTO companies_projects(project_id, company_id) VALUES (?, ?)";

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SELECT_LAST_COMPANY_INDEX);
            resultSet.next();
            long companId = resultSet.getLong("company_id");

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMPANIES_PROJECTS);
            for (Project project : company.getProjects()) {
                preparedStatement.setLong(1, project.getId());
                preparedStatement.setLong(2, companId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        companyInsert(company, INSERT_COMPANY);
    }

    @Override
    public void update(Company company, Long id) {
        final String UPDATE_COMPANY = "UPDATE companies SET name=?, count_of_employee=?, WHERE id=" + id;
        companyInsert(company, UPDATE_COMPANY);
    }

    //Try another method to delete
    @Override
    public void deleteById(Long id) {
        final String DELETE_ALL_COMPANIES = "DELETE FROM companies WHERE id=" + id;
        try (Statement statement = connection.createStatement()) {
            int i = statement.executeUpdate(DELETE_ALL_COMPANIES);
            if (i == 1) {
                System.out.println("Company with id=" + id + " - deleted!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Company getCompany(ResultSet rs) {
        Company company = new Company();
        try {
            company.setId(rs.getLong("id"));
            company.setName(rs.getString("name"));
            company.setCountOfEmployee(rs.getInt("count_of_employee"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return company;
    }

    private void companyInsert(Company company, String request) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(request)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setInt(2, company.getCountOfEmployee());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e1) {
            System.out.println("Oop`s something wrong - null pointer");
        }
    }
}
