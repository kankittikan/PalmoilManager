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
    @Column(nullable = false)
    private WorkType workType;

    @Column(nullable = false)
    private boolean repeatable;
}
