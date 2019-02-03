package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Developer {
    private Integer id;
    private Integer age;
    private String firstName;
    private String lastName;
    private String sex;
    private Integer salary;
    private String company;
    private String project;

    public Developer(Integer age, String firstName, String lastName, String sex, Integer salary, String company, String project) {
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.salary = salary;
        this.company = company;
        this.project = project;
    }

    public Developer(Integer id, Integer age, String firstName, String lastName, String sex, Integer salary, String company, String project) {
        this.id = id;
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.salary = salary;
        this.company = company;
        this.project = project;
    }

    public Developer() {
    }

}
