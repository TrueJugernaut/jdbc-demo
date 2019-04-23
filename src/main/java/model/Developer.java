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

    @Override
    public String toString() {
        return "Developer {" +
                "id=" + id +
                ", \n\tage=" + age +
                ", \n\tfirstName='" + firstName + '\'' +
                ", \n\tlastName='" + lastName + '\'' +
                ", \n\tsex='" + sex + '\'' +
                ", \n\tsalary=" + salary +
                ", \n\tcompany=" + company +
                ", \n\tprojects=" + projects +
                ", \n\tskill=" + skill +
                "\n}";
    }
}
