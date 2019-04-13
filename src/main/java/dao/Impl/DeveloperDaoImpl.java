package dao.Impl;

import dao.AbstractDao;
import dao.DeveloperDao;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DeveloperDaoImpl extends AbstractDao implements DeveloperDao {

    public DeveloperDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Developer findById(Long id) {
        final String SELECT_DEVELOPER = "SELECT * FROM developer WHERE id=" + id;
        final String SELECT_SKILL_ID = "SELECT skill_id FROM developer WHERE id=" + id;
        final String SELECT_SKILL = "SELECT * FROM skill WHERE id=";
        final String SELECT_ALL_PROJECTS = "SELECT * FROM projects_developers WHERE developer_id=" + id;
        final String SELECT_COMPANY = "SELECT company_id FROM developer WHERE developer_id=" + id;

        Developer developer = null;
        Skill skill = null;
        Company company = null;
        Project project = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_DEVELOPER);
            if (!resultSet.next()) {
                return developer;
            }
            developer = getDeveloper(resultSet);

            resultSet = statement.executeQuery(SELECT_SKILL_ID);
            Long skillID = resultSet.getLong("id");
            resultSet = statement.executeQuery(SELECT_SKILL + skillID);
            skill = getSkill(resultSet);
            developer.setSkill(skill);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    @Override
    public List<Developer> findAll() {
        final String SELECT_ALL_DEVELOPERS = "SELECT * FROM developer";
        List<Developer> developers = new ArrayList<>();
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

        return developers;
    }


    @Override
    public void insert(Developer developer) {
        final String INSERT_DEVELOPER =
                "INSERT into developer(age, firstName, lastName, sex, salary) VALUES (?, ?, ?, ?, ?)";
        final String SELECT_LAST_DEVELOPER_INDEX =
                "SELECT MAX(id) FROM developers";
        final String INSERT_SKILL_FOR_DEVELOPER =
                "INSERT INTO skill(technology, seniority, developer_id) VALUES(?, ?, ?)";
        final String INSERT_DEVELOPER_PROJECT =
                "INSERT INTO projects_developers(developer_id, projects_id) VALUES (?, ?)";
        final String INSERT_DEVELOPER_COMPANY =
                "INSERT INTO developer (company_id) VALUES (?)";
        try {
            insertDeveloper(developer, INSERT_DEVELOPER);
            PreparedStatement pr;

            Skill skill = null;
            pr = connection.prepareStatement(INSERT_SKILL_FOR_DEVELOPER);
            pr.setLong(1, skill.getId());
            pr.setString(2, String.valueOf(skill.getTechnology()));
            pr.setString(3, String.valueOf(skill.getSeniority()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Developer dev, Long id) {

        final String UPDATE_DEVELOPER =
                "UPDATE developer SET age=?, firstName=?, lastName=?, sex=?, salary=? WHERE id=" + id;
        final String UPDATE_SKILLS_FOR_DEVELOPER =
                "UPDATE skills SET technology=?, seniority=? WHERE id=?";
        try {
            insertDeveloper(dev, UPDATE_DEVELOPER);
//Update skills
//            pr = connection.prepareStatement(UPDATE_SKILLS_FOR_DEVELOPER);
//            for (Skill skill : dev.getSkills()) {
//                pr.setString(1, String.valueOf(skill.getTechnology()));
//                pr.setString(2, String.valueOf(skill.getSeniority()));
//                pr.setLong(3, skill.getId());
//                pr.execute();
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        final String DELETE_USER_BY_ID =
                "DELETE FROM developer WHERE id=?";
        final String DELETE_SKILLS_BY_DEV_ID =
                "DELETE FROM skill WHERE developer_id=?";
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

    @Override
    public void deleteAll() {
        final String DELETE_USERS =
                "DELETE FROM developer;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void insertDeveloper(Developer developer, String INSERT_DEVELOPER) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(INSERT_DEVELOPER);
        pr.setInt(1, developer.getAge());
        pr.setString(2, developer.getFirstName());
        pr.setString(3, developer.getLastName());
        pr.setString(4, developer.getSex());
        pr.setDouble(5, developer.getSalary());
        pr.execute();
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
}