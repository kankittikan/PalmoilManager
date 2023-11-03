package ku.cs.palmoilmnger.model;

import ku.cs.palmoilmnger.entity.Description;
import ku.cs.palmoilmnger.entity.WorkRound;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionSummaryDTO {
    private String description;
    private String descriptionType;
    private String value;

    public TransactionSummaryDTO(String description, String descriptionType, String value) {
        this.description = description;
        this.descriptionType = descriptionType;
        this.value = value;
    }

    public TransactionSummaryDTO() {
    }
}
