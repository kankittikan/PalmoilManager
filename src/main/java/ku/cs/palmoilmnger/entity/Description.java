package ku.cs.palmoilmnger.entity;

import jakarta.persistence.*;
import ku.cs.palmoilmnger.common.DescriptionType;
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

    @Enumerated
    private DescriptionType descriptionType;
    private boolean repeatable;

    public Description(WorkType workType, String name, DescriptionType descriptionType, boolean repeatable) {
        this.workType = workType;
        this.name = name;
        this.descriptionType = descriptionType;
        this.repeatable = repeatable;
    }

    public Description() {

    }
}
