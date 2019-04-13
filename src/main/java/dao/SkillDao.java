package dao;

import model.Skill;

public interface SkillDao extends CruidDao<Skill> {

    void findByTypeofSkill(Skill.Technology technology);

    void findBySeniority(Skill.Seniority seniority);
}
