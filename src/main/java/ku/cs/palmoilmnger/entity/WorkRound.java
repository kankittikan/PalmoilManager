package ku.cs.palmoilmnger.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class WorkRound {
    @Id
    @Column(length = 8)
    private String idWorkRound;

    @ManyToOne
    @Column(nullable = false)
    private Plantation plantation;

    @ManyToOne
    @Column(nullable = false)
    private Manager manager;
}
