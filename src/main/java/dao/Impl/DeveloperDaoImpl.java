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
        final String SELECT_DEVELOPER = "SELECT * FROM developers WHERE id=" + id;
        final String SELECT_SKILL_ID = "SELECT skill_id FROM developers WHERE id=" + id;
        final String SELECT_SKILL = "SELECT * FROM skills WHERE id=";
        final String SELECT_ALL_PROJECTS = "SELECT * FROM developers_projects WHERE developer_id=" + id;
        final String SELECT_COMPANY_ID = "SELECT company_id FROM developers WHERE id=" + id;
        final String SELECT_COMPANY = "SELECT * FROM companies WHERE id=";

        Developer developer = null;
        Skill skill = null;
        Company company = null;
        Project project = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_DEVELOPER);
            if (!resultSet.next()) return developer;
            developer = getDeveloper(resultSet);

//Get Skill id
            resultSet = statement.executeQuery(SELECT_SKILL_ID);
            long skillId = 0;
            if (resultSet.next()) skillId = resultSet.getLong("skill_id");

            System.out.println("SKILL ID " + skillId);

            resultSet = statement.executeQuery(SELECT_SKILL + skillId);
            if (resultSet.next()) developer.setSkill(getSkill(resultSet));

//Get Company id
            resultSet = statement.executeQuery(SELECT_COMPANY_ID);
            long companyId = 0;
            if (resultSet.next()) companyId = resultSet.getLong("company_id");

            resultSet = statement.executeQuery(SELECT_COMPANY + companyId);
            if (resultSet.next()) developer.setCompany(getCompany(resultSet));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    @Override
    public List<Developer> findAll() {
        final String SELECT_ALL_DEVELOPERS = "SELECT * FROM developers";
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
                "INSERT into developers(age, first_name, last_name, sex, salary, skill_id, company_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        final String SELECT_LAST_DEVELOPER_INDEX = "SELECT MAX(id) FROM developers";

        final String INSERT_SKILL = "INSERT INTO skills(technology, seniority) VALUES(?, ?)";

        final String INSERT_DEVELOPER_PROJECT = "INSERT INTO developers_projects(developer_id, project_id) VALUES (?, ?)";

        final String INSERT_NEW_COMPANY = "INSERT INTO companies(name, count_of_employee) VALUES (?, ?)";

        try {
            PreparedStatement pr;

//ADD SKILLS
            Skill skill = developer.getSkill();
            pr = connection.prepareStatement(INSERT_SKILL);
            pr.setString(1, String.valueOf(skill.getTechnology()));
            pr.setString(2, String.valueOf(skill.getSeniority()));
            pr.execute();

//ADD COMPANY
            Company company = developer.getCompany();
            pr = connection.prepareStatement(INSERT_NEW_COMPANY);
            pr.setString(1, company.getName());
            pr.setInt(2, company.getCountOfEmployee());
            pr.execute();

//ADD PROJECT

            insertDeveloper(developer, INSERT_DEVELOPER);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Developer dev, Long id) {
//
//        final String UPDATE_DEVELOPER =
//                "UPDATE developers SET age=?, first_name=?, last_name=?, sex=?, salary=? WHERE id=" + id;
//        final String UPDATE_SKILLS_FOR_DEVELOPER =
//                "UPDATE skills SET technology=?, seniority=? WHERE id=?";
//        try {
//            insertDeveloper(dev, UPDATE_DEVELOPER, "");
//Update skills
//            pr = connection.prepareStatement(UPDATE_SKILLS_FOR_DEVELOPER);
//            for (Skill skill : dev.getSkills()) {
//                pr.setString(1, String.valueOf(skill.getTechnology()));
//                pr.setString(2, String.valueOf(skill.getSeniority()));
//                pr.setLong(3, skill.getId());
//                pr.execute();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void deleteById(Long id) {
        final String DELETE_USER_BY_ID =
                "DELETE FROM developers WHERE id=?";
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

    @Override
    public void deleteAll() {
        final String DELETE_USERS =
                "DELETE FROM developers;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void insertDeveloper(Developer developer, String INSERT_DEVELOPER) throws SQLException {

        final String SELECT_LAST_SKILL_ID = "SELECT MAX(id) AS id FROM skills";

        final String SELECT_LAST_COMPANY_ID = "SELECT MAX(id) AS id FROM companies";

        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DEVELOPER);

        ResultSet resultSet = preparedStatement.executeQuery(SELECT_LAST_SKILL_ID);
        resultSet.next();
        long lastSkillId = resultSet.getLong("id");
        System.out.println("LAST SKILL ID" + lastSkillId);

        ResultSet resultSet1 = preparedStatement.executeQuery(SELECT_LAST_COMPANY_ID);
        resultSet1.next();
        long lastCompanyId = resultSet1.getLong("id");
        System.out.println("LAST COMPANY ID" + lastCompanyId);

        preparedStatement.setInt(1, developer.getAge());
        preparedStatement.setString(2, developer.getFirstName());
        preparedStatement.setString(3, developer.getLastName());
        preparedStatement.setString(4, developer.getSex());
        preparedStatement.setDouble(5, developer.getSalary());
        preparedStatement.setLong(6, lastSkillId);
        preparedStatement.setLong(7, lastCompanyId);
        preparedStatement.execute();
    }

    private Developer getDeveloper(ResultSet resultSet) throws SQLException {
        return Developer.builder()
                .id(resultSet.getLong("id"))
                .age(resultSet.getInt("age"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
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
                .countOfEmployee(resultSet.getInt("count_of_employee"))
                .projects(new HashSet<Project>())
                .build();
    }
}