package ku.cs.palmoilmnger.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class WorkType {
    @Id
    @GeneratedValue
    private int idWorkType;

    @Column(nullable = false)
    private String name;
}
