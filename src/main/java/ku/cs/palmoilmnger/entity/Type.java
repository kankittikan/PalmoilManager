package ku.cs.palmoilmnger.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Type {
    @Id
    @GeneratedValue
    private int idType;

    private String name;
}
