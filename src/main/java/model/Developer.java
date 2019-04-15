package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Developer {
    private long id;
    private Integer age;
    private String firstName;
    private String lastName;
    private String sex;
    private Double salary;
    private Company company;
    private List<Project> projects;
    private Skill skill;

    public void addProject(Project project) {
        projects.add(project);
    }

}
