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
    private Plantation plantation;

    @ManyToOne
    private Manager manager;

    public WorkRound(String idWorkRound, Plantation plantation, Manager manager) {
        this.idWorkRound = idWorkRound;
        this.plantation = plantation;
        this.manager = manager;
    }

    public WorkRound() {

    }
}
