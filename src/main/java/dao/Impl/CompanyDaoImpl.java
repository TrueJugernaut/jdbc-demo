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

    //Need to add projects
    @Override
    public Company findById(Long id) {
        final String SELECT_COMPANY_BY_ID = "SELECT * FROM companies WHERE id=" + id;
        final String SELECT_ALL_PROJECTS = "SELECT projects.id, projects.name, projects.customer_id\n" +
                "                FROM projects\n" +
                "                INNER JOIN companies_projects\n" +
                "                ON projects.id = companies_projects.project_id\n" +
                "                INNER JOIN companies\n" +
                "                ON companies_projects.company_id = companies.id\n" +
                "                WHERE company_id =" + id + ";";

        Company company = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_COMPANY_BY_ID);
            if (!resultSet.next()) {
                System.out.println("Company with id " + id + " doesn't exist: ");
                return null;
            }
            company = getCompany(resultSet);
            List<Project> projects = new ArrayList<>();
            resultSet = statement.executeQuery(SELECT_ALL_PROJECTS);
            while (resultSet.next()) {
                projects.add(Project.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .build());
            }
            company.setProjects(projects);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return company;
    }

    //Need to add projects
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
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMPANY);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setInt(2, company.getCountOfEmployee());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Something goes wrong with Prepared Statement here CompanyDaoImpl.insert()");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Company company, Long id) {
        final String UPDATE_COMPANY = "UPDATE company SET name=?, count_of_employee=?, WHERE id=" + id;
        try {
            insertUpdate(company, UPDATE_COMPANY);
        } catch (SQLException e) {
            System.out.println("Something goes wrong with Prepared Statement here CompanyDaoImpl.update()");
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
        company.setCountOfEmployee(rs.getInt("count_of_employee"));
        return company;
    }

    private void insertUpdate(Company com, String request) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(request);
        preparedStatement.setString(1, com.getName());
        preparedStatement.setInt(2, com.getCountOfEmployee());

        int i = preparedStatement.executeUpdate();

        if (i == 1) {
            System.out.println("Company updated/created");
        }
    }
}
