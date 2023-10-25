package ku.cs.palmoilmnger.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private int idTransaction;

    @ManyToOne
    @Column(nullable = false)
    private WorkRound workRound;

    @ManyToOne
    @Column(nullable = false)
    private Description description;

    @ManyToOne
    @Column(nullable = false)
    private WorkType workType;

    @Column(nullable = false)
    private double value;

    private String imageLink;
}
