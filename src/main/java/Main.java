import dao.Impl.*;
import dao.SkillDao;
import model.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectionFactory.getConnection();
        DeveloperService developerService = new DeveloperServiceImpl(new DeveloperDaoImpl(connection));
        CompanyService companyService = new CompanyServiceImpl(new CompanyDaoImpl(connection));
        CustomerService customerService = new CustomerServiceImpl(new CustomerDaoImpl(connection));
        ProjectService projectService = new ProjectServiceImpl(new ProjectDaoImpl(connection));
        SkillDao skillDao = new SkillDaoImpl(connection);

//        Skill skill = Skill.builder()
//                .technology(Skill.Technology.C_SHARP)
//                .seniority(Skill.Seniority.MIDDLE)
//                .build();
//
//        Skill skill1 = Skill.builder()
//                .technology(Skill.Technology.OBJECTIVE_C)
//                .seniority(Skill.Seniority.SENIOR)
//                .build();
//        skillDao.insert(skill1);
//
//        Company company = Company.builder()
//                .name("Пейко")
//                .countOfEmployee(13)
//                .build();
//
//        Customer customer = Customer.builder()
//                .name("Nick")
//                .region("Region")
//                .build();
//
//        List<Customer> customers = new ArrayList<>();
//        int a = 5;
//        while (a != 0) {
//            customers.add(customer);
//            a--;
//        }
//
//        Project project = Project.builder()
//                .name("Bigrate")
//                .customer(customer)
//                .build();
//
//        List<Project> projects = new ArrayList<>();
//        while (a != 0) {
//            projects.add(project);
//            projectService.insert(project);
//            a--;
//        }

//        System.out.println("SELECT COMPANY BY ID " + companyService.findById(4L).toString());
//        List<Company> companies = companyService.findAll();
//        System.out.println("SELECT ALL COMPANIES");
//        for (int i = 0; i < companies.size(); i++) {
//            System.out.println(companies.get(i).toString());
//        }


//        long id = 33L;
//
//        Developer developer = Developer.builder()
//                .age(24)
//                .firstName("Егор")
//                .lastName("Нестеров")
//                .sex("муж")
//                .salary(1200.0)
//                .skill(skill)
//                .company(company)
//                .projects(projects)
//                .build();
//        developerService.insert(developer);
//        System.out.println("CREATED DEVELOPER " + developer.toString());
//
//
//        Developer developer1 = Developer.builder()
//                .age(24)
//                .firstName("BORODA")
//                .lastName("BOGDAN")
//                .sex("male")
//                .salary(1000.0)
//                .build();
//        developerService.update(developer1, 33L);
//        System.out.println("UPDATED DEVELOPER " + developer1.toString());
//
//        Developer developer2 = developerService.findById(id);
//        System.out.println("FIND DEVELOPER BY ID " + developer2);
//        System.out.println("DELETED DEVELOPER " + developer2);
//        developerService.delete(id);


//
//        List<Company> companies = companyService.findAll();
//        Project project1 = Project.builder()
//                .name("Project")
//                .coast(1290.3)
//                .customer(customer)
//                .developers(developers1)
//                .companies(companies)
//                .build();
//
//        projectService.insert(project1);

        Main main = new Main();

        Project project2 = ((ProjectServiceImpl) projectService).findById(5L);
        List<Developer> developers0 = developerService.findAllByProject(project2);
        List<Developer> developers1 = developerService.findAllByTechnology(Skill.Technology.valueOf("C_SHARP"));
        List<Developer> developers2 = developerService.findAllBySeniority(Skill.Seniority.valueOf("SENIOR"));

        System.out.println("SELECT ALL DEVELOPERS BY PROJECT\n");
        main.printDeveloper(developers0);

        System.out.println("SELECT ALL DEVELOPERS BY TECHNOLOGY\n");
        main.printDeveloper(developers1);

        System.out.println("SELECT ALL DEVELOPERS BY SENIORITY\n");
        main.printDeveloper(developers2);
    }

    void printDeveloper(List<Developer> developers) {
        for (int i = 0; i < developers.size(); i++) {
            System.out.println(developers.get(i).toString());
        }
    }
}
