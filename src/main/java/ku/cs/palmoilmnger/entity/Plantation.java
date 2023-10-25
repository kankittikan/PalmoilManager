package ku.cs.palmoilmnger.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Plantation {
    @Id
    @GeneratedValue
    private int idPlantation;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String locationLink;

    @Column(nullable = false)
    private int numOfRaiUnit;
}
