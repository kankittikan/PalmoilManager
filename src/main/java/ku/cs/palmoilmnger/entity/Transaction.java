package ku.cs.palmoilmnger.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Transaction {
    @Id
    @Column(length = 11)
    private String idTransaction;

    private String idType;
    private double value;
}
