package ku.cs.palmoilmnger.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class WorkRound {
    @Id
    @Column(length = 11)
    private String idWorkRound;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Plantation plantation;

    @ManyToOne
    private User user;

    public WorkRound(String idWorkRound, Plantation plantation, User user) {
        this.idWorkRound = idWorkRound;
        this.plantation = plantation;
        this.user = user;
    }

    public WorkRound() {

    }
}
