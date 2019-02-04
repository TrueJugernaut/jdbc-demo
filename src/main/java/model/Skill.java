package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    private Integer id;
    private Technology technology;
    private Seniority seniority;
    private Developer owner;

    private enum Technology {
        JAVA,
        CPP,
        C_SHARP,
        OBJECTIVE_C,
        ANDROID,
        PYTHON,
        JS
    }

    private enum Seniority {
        JUNIOR,
        MIDDLE,
        SENIOR
    }
}
