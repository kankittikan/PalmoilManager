package ku.cs.palmoilmnger.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Transaction {
    @Id
    @Column(length = 11)
    private String idTransaction;

    @ManyToOne
    private WorkRound workRound;

    @ManyToOne
    private Type type;

    private double value;
    private String imageLink;

    public Transaction(String idTransaction, WorkRound workRound, Type type, double value, String imageLink) {
        this.idTransaction = idTransaction;
        this.workRound = workRound;
        this.type = type;
        this.value = value;
        this.imageLink = imageLink;
    }

    public Transaction() {

    }
}
