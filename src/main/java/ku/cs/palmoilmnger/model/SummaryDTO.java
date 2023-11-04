package ku.cs.palmoilmnger.model;

import lombok.Data;

@Data
public class SummaryDTO {
    private double sumOfIncome;
    private double sumOfOutcome;
    private double sumOfWeight;
    private double npm;
    private double np;
    private double productivityRate;

    public void addSumOfIncome(double v) {
        sumOfIncome += v;
    }
    public void addSumOfOutcome(double v) {
        sumOfOutcome += v;
    }

    public void addSumOfWeight(double v) {
        sumOfWeight += v;
    }
}
