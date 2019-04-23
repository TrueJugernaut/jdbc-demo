package service.Impl;

import dao.DeveloperDao;
import model.Developer;
import model.Project;
import model.Skill;
import service.DeveloperService;

import java.util.List;

public class DeveloperServiceImpl implements DeveloperService {

    private DeveloperDao developerDao;

    public DeveloperServiceImpl (DeveloperDao developerDao) {
        this.developerDao = developerDao;
    }

    @Override
    public Developer findById(Long id) {
        return developerDao.findById(id);
    }

    @Override
    public List<Developer> findAll() {
        return developerDao.findAll();
    }

    @Override
    public void insert(Developer developer) {
        developerDao.insert(developer);
    }

    @Override
    public void update(Developer developer, Long id) {
        developerDao.update(developer, id);
    }

    @Override
    public void delete(Long id) {
        developerDao.deleteById(id);
    }

    @Override
    public void deleteAll() {
        developerDao.deleteAll();
    }

    @Override
    public List<Developer> findAllByTechnology(Skill.Technology technology) {
        return developerDao.findAllByTechnology(technology);
    }

    @Override
    public List<Developer> findAllBySeniority(Skill.Seniority seniority) {
        return developerDao.findAllBySeniority(seniority);
    }

    @Override
    public List<Developer> findAllByFirstName(String firstName) {
        return developerDao.findAllByFirstName(firstName);
    }

    @Override
    public List<Developer> findAllByProject(Project project) {
        return developerDao.findAllByProject(project);
    }
}
