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
    private Company company;
    private Customer customer;
    private Set<Developer> developers;
}
