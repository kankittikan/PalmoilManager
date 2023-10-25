package ku.cs.palmoilmnger.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Manager {
    @Id
    @GeneratedValue
    private int idManager;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    public Manager(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }

    public Manager() {

    }
}
