package dao.Impl;

import dao.AbstractDao;
import dao.SkillDao;
import model.Skill;

import java.sql.*;
import java.util.List;

public class SkillDaoImpl extends AbstractDao implements SkillDao {

    public SkillDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Skill findById(Long id) {
        final String SELECT_SKILL_BY_ID = "SELECT * FROM skills WHERE id=" + id;

        Skill skill = new Skill();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_SKILL_BY_ID);
            if (resultSet.next()) {
                skill = Skill.builder()
                        .id(resultSet.getLong("id"))
                        .technology(Skill.Technology.valueOf(resultSet.getString("technology")))
                        .seniority(Skill.Seniority.valueOf(resultSet.getString("seniority")))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e1) {
            System.out.println("Object Skill with id=" + id + " does not exist");
        }
        return skill;
    }

    @Override
    public List<Skill> findAll() {
        return null;
    }

    @Override
    public void insert(Skill skill) {
        final String INSERT_SKILL = "INSERT INTO skills(technology, seniority) VALUES(?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SKILL);
            preparedStatement.setString(1, String.valueOf(skill.getTechnology()));
            preparedStatement.setString(2, String.valueOf(skill.getSeniority()));
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Skill skill, Long id) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void findByTypeofSkill(Skill.Technology technology) {

    }

    @Override
    public void findBySeniority(Skill.Seniority seniority) {

    }
}
