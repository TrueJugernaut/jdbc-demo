import dao.Impl.*;
import dao.SkillDao;
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
        SkillDao skillDao = new SkillDaoImpl(connection);

        Skill skill = Skill.builder()
                .technology(Skill.Technology.C_SHARP)
                .seniority(Skill.Seniority.MIDDLE)
                .build();

        Skill skill1 = Skill.builder()
                .technology(Skill.Technology.OBJECTIVE_C)
                .seniority(Skill.Seniority.SENIOR)
                .build();
        skillDao.insert(skill1);
        System.out.println("NEW SKILL: " + skillDao.findById(190L).toString());

        Company company = Company.builder()
                .name("Пейко")
                .countOfEmployee(13)
                .build();

//        int x = 10;
//        while (x != 0) {
//            companyService.insert(company);
//            x--;
//        }

        System.out.println(companyService.findById(4L).toString());
        List<Company> companies = companyService.findAll();
        for (int i = 0; i < companies.size(); i++) {
            System.out.println(companies.get(i).toString());
        }


        Developer developer = Developer.builder()
                .age(24)
                .firstName("Егор")
                .lastName("Нестеров")
                .sex("муж")
                .salary(1200.0)
                .skill(skill)
                .company(company)
                .build();
        developerService.insert(developer);

        System.out.println(developerService.findById(678L));
        List<Developer> developers = developerService.findAll();
        for (int i = 0; i < developers.size(); i++) {
            System.out.println(developers.get(i));
        }

//        Developer developer1 = developerService.findById(1L);
//        System.out.println(developer1.toString());


//        Developer developer1 = Developer.builder()
//                .age(24)
//                .firstName("Elly")
//                .lastName("Borovska")
//                .sex("female")
//                .salary(1000.0)
//                .build();
//        developerService.update(developer1, 2L);
//        developerService.deleteAll();
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
