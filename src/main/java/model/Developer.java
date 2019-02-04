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
    private Long id;
    private Integer age;
    private String firstName;
    private String lastName;
    private String sex;
    private Double salary;
    private Set<Company> companies;
    private Set<Project> projects;
    private Set<Skill> skills;
}
