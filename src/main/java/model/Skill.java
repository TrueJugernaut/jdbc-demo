package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "owner")
public class Skill {
    private Integer id;
    private Technology technology;
    private Seniority seniority;
    private Developer owner;

    public enum Technology {
        JAVA,
        CPP,
        C_SHARP,
        OBJECTIVE_C,
        ANDROID,
        PYTHON,
        JS
    }

    public enum Seniority {
        JUNIOR,
        MIDDLE,
        SENIOR
    }
}