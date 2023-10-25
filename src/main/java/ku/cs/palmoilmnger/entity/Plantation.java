package ku.cs.palmoilmnger.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Plantation {
    @Id
    @GeneratedValue
    private String idPlantation;

    private String name;
    private String locationLink;
}
