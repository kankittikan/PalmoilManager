package ku.cs.palmoilmnger.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import ku.cs.palmoilmnger.common.WorkTypeName;
import lombok.Data;

@Entity
@Data
public class WorkType {
    @Id
    @GeneratedValue
    private int IdWorkType;

    @Enumerated
    private WorkTypeName workTypeName;

    public WorkType(WorkTypeName workTypeName) {
        this.workTypeName = workTypeName;
    }

    public WorkType() {

    }
}
