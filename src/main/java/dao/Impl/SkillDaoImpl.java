package dao.Impl;

import dao.AbstractDao;
import dao.SkillDao;
import model.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SkillDaoImpl extends AbstractDao implements SkillDao {

    public SkillDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Skill findById(Long id) {
        return null;
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
