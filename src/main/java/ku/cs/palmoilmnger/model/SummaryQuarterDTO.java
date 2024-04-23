package ku.cs.palmoilmnger.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SummaryQuarterDTO extends SummaryAnnualDTO{
    String quarter;
}
