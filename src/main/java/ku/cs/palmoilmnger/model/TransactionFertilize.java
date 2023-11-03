package ku.cs.palmoilmnger.model;

import jakarta.persistence.*;
import ku.cs.palmoilmnger.entity.Description;
import ku.cs.palmoilmnger.entity.WorkRound;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionFertilize {
    private String idTransaction;
    private WorkRound workRound;
    private Description description;
    private String value;
    private String imageLink;
    private LocalDateTime dateAdded;
}
