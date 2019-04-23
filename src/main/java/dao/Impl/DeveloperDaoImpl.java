package dao.Impl;

import dao.AbstractDao;
import dao.DeveloperDao;
import dao.SkillDao;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DeveloperDaoImpl extends AbstractDao implements DeveloperDao {

    public DeveloperDaoImpl(Connection connection) {
        super(connection);
    }

    //    Single responsibility foul I think
    @Override
    public Developer findById(Long id) {
        final String SELECT_DEVELOPER = "SELECT * FROM developers WHERE id=" + id;
        final String SELECT_SKILL_ID = "SELECT skill_id FROM developers WHERE id=" + id;
        final String SELECT_SKILL = "SELECT * FROM skills WHERE id=";
        final String SELECT_COMPANY_ID = "SELECT company_id FROM developers WHERE id=" + id;
        final String SELECT_COMPANY = "SELECT * FROM companies WHERE id=";
        final String SELECT_ALL_PROJECTS = "SELECT projects.id, projects.name\n" +
                "                FROM projects\n" +
                "                INNER JOIN developers_projects\n" +
                "                ON projects.id = developers_projects.project_id\n" +
                "                INNER JOIN developers\n" +
                "                ON developers_projects.developer_id = developers.id\n" +
                "                WHERE developer_id =" + id + ";";

        Developer developer = new Developer();
        Skill skill = new Skill();
        Company company = new Company();
        List<Project> projects = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_DEVELOPER);
            if (!resultSet.next()) developer = getDeveloper(resultSet);

//Select Skill id
            resultSet = statement.executeQuery(SELECT_SKILL_ID);
            long skillId = 0;
            if (resultSet.next()) skillId = resultSet.getLong("skill_id");

            resultSet = statement.executeQuery(SELECT_SKILL + skillId);
            if (resultSet.next()) developer.setSkill(getSkill(resultSet));

//Select Company id
            resultSet = statement.executeQuery(SELECT_COMPANY_ID);
            long companyId = 0;
            if (resultSet.next()) companyId = resultSet.getLong("company_id");

            resultSet = statement.executeQuery(SELECT_COMPANY + companyId);
            if (resultSet.next()) developer.setCompany(getCompany(resultSet));

//Select all projects
            resultSet = statement.executeQuery(SELECT_ALL_PROJECTS);
            while (resultSet.next()) {
                getProject(resultSet);
            }
            developer.setProjects(projects);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e1) {
            System.out.println("Object Developer with id=" + id + " does not exist");
        }
        return developer;
    }

    @Override
    public List<Developer> findAll() {
        final String SELECT_ALL_DEVELOPERS = "SELECT * FROM developers";
        return getDevelopersBy(SELECT_ALL_DEVELOPERS);
    }


    @Override
    public void insert(Developer developer) {
        final String INSERT_DEVELOPER =
                "INSERT into developers(age, first_name, last_name, sex, salary, skill_id, company_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        final String SELECT_LAST_DEVELOPER_INDEX = "SELECT MAX(id) AS id FROM developers";

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
            ResultSet resultSet = pr.executeQuery(SELECT_LAST_DEVELOPER_INDEX);
            resultSet.next();
            long lastDevId = resultSet.getLong("id");

            pr = connection.prepareStatement(INSERT_DEVELOPER_PROJECT);
            for (Project project : developer.getProjects()) {
                pr.setLong(2, project.getId());
                pr.setLong(1, lastDevId);
                pr.execute();
            }

            insertDeveloper(developer, INSERT_DEVELOPER);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Developer developer, Long id) {

        final String UPDATE_DEVELOPER =
                "UPDATE developers SET age=?, first_name=?, last_name=?, sex=?, salary=?, skill_id=?, company_id=? WHERE id=" + id;
        final String UPDATE_SKILLS_FOR_DEVELOPER =
                "UPDATE skills SET technology=?, seniority=? WHERE id=" + id;
        try (PreparedStatement pr = connection.prepareStatement(UPDATE_SKILLS_FOR_DEVELOPER)) {
            insertDeveloper(developer, UPDATE_DEVELOPER);
//Update skills
            Skill skill = developer.getSkill();
            Company company = developer.getCompany();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        final String DELETE_DEVELOPER_BY_ID =
                "DELETE FROM developers WHERE id=" + id;
        final String DELETE_RELATIONS_WITH_PROJECTS_BY_DEV_ID =
                "DELETE FROM developers_projects WHERE developer_id=" + id;

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(DELETE_RELATIONS_WITH_PROJECTS_BY_DEV_ID);
            statement.executeUpdate(DELETE_DEVELOPER_BY_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        final String DELETE_ALL_DEVELOPERS =
                "DELETE FROM developers;";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE_ALL_DEVELOPERS);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Developer> findAllByTechnology(Skill.Technology technology) {
        final String SELECT_ALL_BY_TECHNOLOGY = "SELECT *\n" +
                "FROM developers\n" +
                "LEFT JOIN skills ON developers.skill_id = skills.id\n" +
                "WHERE skills.technology = \"" + technology + "\";";
        return getDevelopersBy(SELECT_ALL_BY_TECHNOLOGY);
    }

    @Override
    public List<Developer> findAllBySeniority(Skill.Seniority seniority) {
        final String SELECT_ALL_BY_SENIORITY = "SELECT *\n" +
                "FROM developers\n" +
                "LEFT JOIN skills ON developers.skill_id = skills.id\n" +
                "WHERE skills.seniority = \"" + seniority + "\";";
        return getDevelopersBy(SELECT_ALL_BY_SENIORITY);
    }

    @Override
    public List<Developer> findAllByFirstName(String firstName) {
        final String SELECT_ALL_BY_FIRST_NAME = "SELECT * from developers WHERE first_name=" + firstName;
        return getDevelopersBy(SELECT_ALL_BY_FIRST_NAME);
    }

    @Override
    public List<Developer> findAllByProject(Project project) {
        final String SELECT_ALL_BY_PROJECT = "SELECT *\n" +
                "FROM developers\n" +
                "INNER JOIN developers_projects                \n" +
                "ON developers.id = developers_projects.developer_id\n" +
                "INNER JOIN projects\n" +
                "ON developers_projects.project_id = projects.id\n" +
                "WHERE project_id ="+ project.getId() +";";
        return getDevelopersBy(SELECT_ALL_BY_PROJECT);
    }

    private List<Developer> getDevelopersBy(String query) {
        List<Developer> developers = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Developer developer = getDeveloper(resultSet);
                developers.add(developer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return developers;
    }

    private void insertDeveloper(Developer developer, String INSERT_DEVELOPER) throws SQLException {

        final String SELECT_LAST_SKILL_ID = "SELECT MAX(id) AS id FROM skills";

        final String SELECT_LAST_COMPANY_ID = "SELECT MAX(id) AS id FROM companies";

        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DEVELOPER);

        ResultSet resultSet = preparedStatement.executeQuery(SELECT_LAST_SKILL_ID);
        resultSet.next();
        long lastSkillId = resultSet.getLong("id");

        ResultSet resultSet1 = preparedStatement.executeQuery(SELECT_LAST_COMPANY_ID);
        resultSet1.next();
        long lastCompanyId = resultSet1.getLong("id");

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
                .id(resultSet.getLong("id"))
                .technology(Skill.Technology.valueOf(resultSet.getString("technology")))
                .seniority(Skill.Seniority.valueOf(resultSet.getString("seniority")))
                .build();
    }

    private Project getProject(ResultSet resultSet) throws SQLException {
        return Project.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }

    private Company getCompany(ResultSet resultSet) throws SQLException {
        return Company.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .countOfEmployee(resultSet.getInt("count_of_employee"))
                .build();
    }
}