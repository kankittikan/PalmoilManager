package ku.cs.palmoilmnger.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Manager {
    @Id
    @GeneratedValue
    private int idManager;

    private String username;
    private String name;
    private String password;
}
