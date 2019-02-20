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
public class Company {
    private Long id;
    private String name;
    private Integer countOfEmployee;
    private Set<Project> projects;

    public void addProject(Project project) {
        projects.add(project);
    }
}
