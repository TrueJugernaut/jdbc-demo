package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    private Long id;
    private String name;
    private Integer countOfEmployee;
    private List<Project> projects;

    public void addProject(Project project) {
        projects.add(project);
    }
}
