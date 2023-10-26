package ku.cs.palmoilmnger.service.summary;

import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.entity.WorkRound;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class AnnualPDFExporter extends PDFExporter{
    public AnnualPDFExporter(Report report, Plantation plantation, WorkRound workRound, List<Transaction> palmTransaction, List<Transaction> fertilizerTransaction, List<Transaction> trimTransaction) throws IOException {
        super(report, plantation, workRound, palmTransaction, fertilizerTransaction, trimTransaction);
    }

    @Override
    public void export() throws FileNotFoundException {

    }
}
