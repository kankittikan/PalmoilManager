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
    @JoinColumn(nullable = false)
    private WorkRound workRound;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Description description;

    @ManyToOne
    @JoinColumn(nullable = false)
    private WorkType workType;

    @Column(nullable = false)
    private double value;

    private String imageLink;

    public Transaction(WorkRound workRound, Description description, WorkType workType, double value, String imageLink) {
        this.workRound = workRound;
        this.description = description;
        this.workType = workType;
        this.value = value;
        this.imageLink = imageLink;
    }

    public Transaction() {

    }
}
