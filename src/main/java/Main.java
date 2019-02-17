import dao.Impl.DeveloperDaoImpl;
import model.Company;
import model.Customer;
import model.Developer;
import model.Project;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<Developer> developers = new HashSet<>();

        Set<Project> projects = new HashSet<>();
        projects.add(new Project().builder()
                .name("Bigrate")
                .customer(new Customer())
                .developers(developers)
                .build());

        Set<Company> companies = new HashSet<>();
        companies.add(new Company().builder()
                .name("Peiko")
                .countOfEmployee(13)
                .projects(projects)
                .build());

        Developer developer = Developer.builder()
                .age(24)
                .firstName("Dima")
                .lastName("Harmashev")
                .sex("male")
                .salary(2200.0)
                .companies(companies)
                .projects(projects)
                .build();
//        DeveloperDaoImpl developerDao = new DeveloperDaoImpl();
//        developerDao.insert(developer);
//        System.out.println(developer.toString());

    }
}
