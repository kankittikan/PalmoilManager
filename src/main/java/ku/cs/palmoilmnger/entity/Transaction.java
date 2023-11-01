package ku.cs.palmoilmnger.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {
    @Id
    @Column(length = 11)
    private String idTransaction;

    @ManyToOne
    @JoinColumn(nullable = false)
    private WorkRound workRound;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Description description;

    @Column(nullable = false)
    private double value;

    private String imageLink;
    private LocalDateTime dateAdded;

    public Transaction(WorkRound workRound, Description description, double value, String imageLink) {
        this.workRound = workRound;
        this.description = description;
        this.value = value;
        this.imageLink = imageLink;
    }

    public Transaction() {

    }
}
