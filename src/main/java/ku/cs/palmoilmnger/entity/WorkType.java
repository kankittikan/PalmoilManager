package ku.cs.palmoilmnger.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class WorkType {
    @Id
    @GeneratedValue
    private int IdWorkType;

    private String name;

    public WorkType(String name) {
        this.name = name;
    }

    public WorkType() {

    }
}
