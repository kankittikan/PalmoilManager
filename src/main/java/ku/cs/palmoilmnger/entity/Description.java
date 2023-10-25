package ku.cs.palmoilmnger.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Description {
    @Id
    @GeneratedValue
    private int idDescription;

    @ManyToOne
    private WorkType workType;

    private String name;
    private boolean repeatable;


    public Description(String name, WorkType workType, boolean repeatable) {
        this.name = name;
        this.workType = workType;
        this.repeatable = repeatable;
    }

    public Description() {

    }
}
