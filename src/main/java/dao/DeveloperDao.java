package dao;

import model.Developer;

import java.util.List;
import java.util.Set;

public interface DeveloperDao extends CruidDao<Developer> {

    void deleteAll();
}
