package ku.cs.palmoilmnger.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Manager {
    @Id
    @GeneratedValue
    private String idManager;

    private String username;
    private String name;
    private String password;
}
