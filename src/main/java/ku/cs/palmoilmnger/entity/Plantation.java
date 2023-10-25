package ku.cs.palmoilmnger.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Plantation {
    @Id
    @GeneratedValue
    private int idPlantation;

    private String name;
    private String locationLink;

    public Plantation(String name, String locationLink) {
        this.name = name;
        this.locationLink = locationLink;
    }

    public Plantation() {

    }
}
