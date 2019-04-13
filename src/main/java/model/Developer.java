package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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
    private Set<Project> projects;
    private Skill skill;

    public void addSkill(Skill skill) {
        this.skill = skill;
    }

    public void addProject(Project project) {
        projects.add(project);
    }
}
