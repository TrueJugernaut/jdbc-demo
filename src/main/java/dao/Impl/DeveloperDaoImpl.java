package dao.Impl;

import dao.AbstractDao;
import dao.DeveloperDao;
import model.*;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeveloperDaoImpl extends AbstractDao implements DeveloperDao {

    public DeveloperDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Developer findById(Long id) {
        final String SELECT_DEVELOPER =
                "SELECT * FROM developer WHERE id=";
        final String SELECT_ALL_SKILLS =
                "SELECT * FROM skills WHERE developer_id=";
        final String SELECT_ALL_PROJECTS =
                "SELECT * FROM developers_projects WHERE developer_id=";
        final String SELECT_ALL_COMPANIES =
                "SELECT * FROM developers_companies WHERE developer_id=";

        Developer developer = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_DEVELOPER + id);
            if (!resultSet.next()) {
                System.out.println("Enter");
                return developer;
            }
            developer = getDeveloper(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    @Override
    public List<Developer> findAll() {
        final String SELECT_ALL_DEVELOPERS =
                "SELECT * FROM developer";
        Set<Developer> developers = new HashSet<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_DEVELOPERS);
            while (resultSet.next()) {
                Developer developer = getDeveloper(resultSet);
                developers.add(developer);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public void insert(Developer dev) {
        final String INSERT_DEVELOPER =
                "INSERT into developer(age, firstName, lastName, sex, salary) VALUES (?, ?, ?, ?, ?)";
        final String SELECT_LAST_DEVELOPER_INDEX =
                "SELECT MAX(id) FROM developers";
        final String INSERT_SKILLS_FOR_DEVELOPER =
                "INSERT INTO skills(technology, seniority, developer_id) VALUES(?, ?, ?)";
        final String INSERT_DEVELOPER_PROJECT =
                "INSERT INTO developers_projects(developer_id, projects_id) VALUES (?, ?)";
        final String INSERT_DEVELOPER_COMPANY =
                "INSERT INTO developers_companies(developer_id, companies_id) VALUES (?, ?)";
        try {
            PreparedStatement pr = connection.prepareStatement(INSERT_DEVELOPER);
            pr.setInt(1, dev.getAge());
            pr.setString(2, dev.getFirstName());
            pr.setString(3, dev.getLastName());
            pr.setString(4, dev.getSex());
            pr.setDouble(5, dev.getSalary());
            pr.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Developer dev) {

        final String UPDATE_DEVELOPER =
                "UPDATE developers SET age=?, firstName=?, lastName=?, sex=?, salary=? WHERE id=?";
        final String UPDATE_SKILLS_FOR_DEVELOPER =
                "UPDATE skills SET technology=?, seniority=? WHERE id=?";
        try {
            PreparedStatement pr = connection.prepareStatement(UPDATE_DEVELOPER);
            pr.setInt(1, dev.getAge());
            pr.setString(2, dev.getFirstName());
            pr.setString(3, dev.getLastName());
            pr.setString(4, dev.getSex());
            pr.setDouble(5, dev.getSalary());
            pr.setLong(6, dev.getId());
            pr.execute();
//Update skills
            pr = connection.prepareStatement(UPDATE_SKILLS_FOR_DEVELOPER);
            for (Skill skill : dev.getSkills()) {
                pr.setString(1, String.valueOf(skill.getTechnology()));
                pr.setString(2, String.valueOf(skill.getSeniority()));
                pr.setLong(3, skill.getId());
                pr.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        final String DELETE_USER_BY_ID =
                "DELETE FROM developer WHERE id=?";
        final String DELETE_SKILLS_BY_DEV_ID =
                "DELETE FROM skills WHERE developer_id=?";
        final String DELETE_RELATIONS_WITH_PROJECTS_BY_DEV_ID =
                "DELETE FROM developers_projects WHERE developer_id=?";

        try {
            PreparedStatement pr = connection.prepareStatement(DELETE_SKILLS_BY_DEV_ID);
            pr.setLong(1, id);

            pr = connection.prepareStatement(DELETE_RELATIONS_WITH_PROJECTS_BY_DEV_ID);
            pr.setLong(1, id);
            pr.execute();

            pr = connection.prepareStatement(DELETE_USER_BY_ID);
            pr.setLong(1, id);
            pr.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Developer getDeveloper(ResultSet resultSet) throws SQLException {
        return Developer.builder()
                .id(resultSet.getLong("id"))
                .age(resultSet.getInt("age"))
                .firstName(resultSet.getString("firstName"))
                .lastName(resultSet.getString("lastName"))
                .sex(resultSet.getString("sex"))
                .salary(resultSet.getDouble("salary"))
                .build();
    }

    private Skill getSkill(ResultSet resultSet) throws SQLException {
        return Skill.builder()
                .id(resultSet.getInt("id"))
                .technology(Skill.Technology.valueOf(resultSet.getString("technology")))
                .seniority(Skill.Seniority.valueOf(resultSet.getString("seniority")))
                .build();
    }

    private Project getProject(ResultSet resultSet) throws SQLException {
        return Project.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .customer(new Customer())
                .companies(new HashSet<Company>())
                .developers(new HashSet<Developer>())
                .build();
    }

    private Company getCompany(ResultSet resultSet) throws SQLException {
        return Company.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .countOfEmployee(resultSet.getInt("count-of-employee"))
                .projects(new HashSet<Project>())
                .build();
    }

//    @Override
//    public Developer findById(Long id) {
//        try (Connection connection = ConnectionFactory.getConnection()) {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM developer WHERE id=" + id);
//            if (resultSet.next()) {
//                return extractDeveloperFromResultSet(resultSet);
//            }
//        } catch (SQLException e) {
//            System.out.println("Something wrong with util");
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public Set findAll() {
//        try (Connection connection = ConnectionFactory.getConnection()) {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM developer");
//
//            Set<Developer> developers = new HashSet<>();
//
//            while (resultSet.next()) {
//                Developer developer = extractDeveloperFromResultSet(resultSet);
//                developers.add(developer);
//            }
//            return developers;
//        } catch (SQLException e) {
//            System.out.println("Something wrong with util");
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public void insert(Developer dev) {
//        String req = "INSERT into developers.developer VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";
//        insertUpdate(dev, req);
//    }
//
//    @Override
//    public void update(Developer dev) {
//        String req = "UPDATE developers.developer SET age=?, firstName=?, lastName=?, sex=?, salary=?, company=?, project=? WHERE id=?";
//        insertUpdate(dev, req);
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        try (Connection connection = ConnectionFactory.getConnection()) {
//            Statement statement = connection.createStatement();
//            int i = statement.executeUpdate("DELETE FROM developer WHERE id=" + id);
//
//            if (i == 1) {
//                System.out.println("Developer with id " + id + " deleted");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private Developer extractDeveloperFromResultSet(ResultSet rs) throws SQLException {
//        Developer developer = new Developer();
//
//        developer.setId(rs.getLong("id"));
//        developer.setAge(rs.getInt("age"));
//        developer.setFirstName(rs.getString("firstName"));
//        developer.setLastName(rs.getString("lastName"));
//        developer.setSalary(rs.getDouble("salary"));
//        developer.setCompanies((Set<Company>) rs.getObject("company"));
//        developer.setProjects((Set<Project>) rs.getObject("project"));
//        developer.setSex(rs.getString("sex"));
//        return developer;
//    }
//
//    private void insertUpdate(Developer dev, String req) {
//        try (Connection connection = ConnectionFactory.getConnection()) {
//            PreparedStatement preparedStatement = connection.prepareStatement(req);
//            preparedStatement.setInt(1, dev.getAge());
//            preparedStatement.setString(2, dev.getFirstName());
//            preparedStatement.setString(3, dev.getLastName());
//            preparedStatement.setString(4, dev.getSex());
//            preparedStatement.setDouble(5, dev.getSalary());
//            int i = preparedStatement.executeUpdate();
//
//            if (i == 1) {
//                System.out.println("Developer updated/created");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


}