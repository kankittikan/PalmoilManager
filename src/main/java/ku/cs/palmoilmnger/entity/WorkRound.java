package ku.cs.palmoilmnger.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class WorkRound {
    @Id
    @Column(length = 8)
    private String idWorkRound;

    private String idPlantation;
    private String idManager;
}
