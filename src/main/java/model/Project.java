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
public class Project {
    private Integer id;
    private String name;
    private Set<Company> companies;
    private Customer customer;
    private Set<Developer> developers;

    public void addDeveloper(Developer developer) {
        developers.add(developer);
    }
}
