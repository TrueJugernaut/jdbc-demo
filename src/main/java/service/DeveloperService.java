package service;

import model.Developer;
import model.Project;
import model.Skill;

import java.util.List;

public interface DeveloperService extends Service<Developer> {

    void deleteAll();

    List<Developer> findAllByTechnology(Skill.Technology technology);

    List<Developer> findAllBySeniority(Skill.Seniority seniority);

    List<Developer> findAllByFirstName(String firstName);

    List<Developer> findAllByProject(Project project);

}
