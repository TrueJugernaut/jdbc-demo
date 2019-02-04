package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Integer id;
    private String name;
    private String region;
    private Set<Project> projects;
    private Set<Company> companies;

    public void addProject(Project project) {
        projects.add(project);
    }

    public void addCompanies(Company company) {
        companies.add(company);
    }

}