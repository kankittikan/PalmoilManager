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
    private WorkRound workRound;

    @ManyToOne
    private Description description;

    @ManyToOne
    private WorkType workType;

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
