package service.Impl;

import dao.DeveloperDao;
import model.Developer;
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
    public void update(Developer developer) {
        developerDao.update(developer);
    }

    @Override
    public void delete(Long id) {
        developerDao.deleteById(id);
    }
}
