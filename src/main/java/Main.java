import dao.Impl.CompanyDaoImpl;
import dao.Impl.CustomerDaoImpl;
import dao.Impl.DeveloperDaoImpl;
import dao.Impl.ProjectDaoImpl;
import model.Company;
import model.Developer;
import model.Skill;
import service.CompanyService;
import service.CustomerService;
import service.DeveloperService;
import service.Impl.CompanyServiceImpl;
import service.Impl.CustomerServiceImpl;
import service.Impl.DeveloperServiceImpl;
import service.Impl.ProjectServiceImpl;
import service.ProjectService;
import util.ConnectionFactory;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectionFactory.getConnection();
        DeveloperService developerService = new DeveloperServiceImpl(new DeveloperDaoImpl(connection));
        CompanyService companyService = new CompanyServiceImpl(new CompanyDaoImpl(connection));
        CustomerService customerService = new CustomerServiceImpl(new CustomerDaoImpl(connection));
        ProjectService projectService = new ProjectServiceImpl(new ProjectDaoImpl(connection));

        Skill skill = Skill.builder()
                .id(1)
                .technology(Skill.Technology.JAVA)
                .seniority(Skill.Seniority.JUNIOR)
                .build();


        Developer developer = Developer.builder()
                .age(24)
                .firstName("Yehor")
                .lastName("Nesterov")
                .sex("male")
                .salary(1200.0)
                .skill(skill)
                .build();
        developerService.insert(developer);

        Company company = Company.builder()
                .name("Peiko")
                .countOfEmployee(13)
                .build();

        companyService.insert(company);
//        companyService.findById(1L);
        List<Company> companies = companyService.findAll();
        for (int i = 0; i < companies.size(); i++) {
            System.out.println(companies.get(i));
        }



        System.out.println(developerService.findById(1L));
        List<Developer> developers = developerService.findAll();
        for (int i = 0; i < developers.size(); i++) {
            System.out.println(developers.get(i));
        }


        Developer developer1 = Developer.builder()
                .age(24)
                .firstName("Elly")
                .lastName("Nesterova")
                .sex("female")
                .salary(1000.0)
                .build();
        developerService.update(developer1, 2L);
        developerService.deleteAll();
//        Set<Developer> developers = new HashSet<>();
//
//        Set<Project> projects = new HashSet<>();
//        projects.add(new Project().builder()
//                .name("Bigrate")
//                .customer(new Customer())
//                .developers(developers)
//                .build());
//
//        Set<Company> companies = new HashSet<>();
//        companies.add(new Company().builder()
//                .name("Peiko")
//                .countOfEmployee(13)
//                .projects(projects)
//                .build());
//
//        Developer developer = Developer.builder()
//                .age(24)
//                .firstName("Dima")
//                .lastName("Harmashev")
//                .sex("male")
//                .salary(2200.0)
//                .companies(companies)
//                .projects(projects)
//                .build();
//        DeveloperDaoImpl developerDao = new DeveloperDaoImpl();
//        developerDao.insert(developer);
//        System.out.println(developer.toString());

    }
}
