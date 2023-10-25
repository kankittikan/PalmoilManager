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

    @Column(nullable = false)
    private String name;

    @Enumerated
    @Column(nullable = false)
    private DescriptionType descriptionType;

    @ManyToOne
    @JoinColumn(nullable = false)
    private WorkType workType;

    @Column(nullable = false)
    private boolean repeatable;

    public Description(String name, DescriptionType descriptionType, WorkType workType, boolean repeatable) {
        this.name = name;
        this.descriptionType = descriptionType;
        this.workType = workType;
        this.repeatable = repeatable;
    }

    public Description() {

    }
}
