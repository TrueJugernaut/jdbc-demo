package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private Long id;
    private String name;
    private Double coast;
    private List<Company> companies;
    private Customer customer;
    private List<Developer> developers;

    @Override
    public String toString() {
        return "Project{" +
                "\n\tid=" + id +
                ", \n\tname='" + name + '\'' +
                ", \n\tcoast='" + coast +
                ", \n\tcompanies=" + companies +
                ", \n\tcustomer=" + customer +
                ", \n\tdevelopers=" + developers +
                "\n}";
    }
}
