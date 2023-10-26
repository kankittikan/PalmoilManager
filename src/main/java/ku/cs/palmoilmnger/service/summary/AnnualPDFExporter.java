package ku.cs.palmoilmnger.service.summary;

import com.lowagie.text.Paragraph;
import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.entity.WorkRound;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class AnnualPDFExporter extends PDFExporter{

    public AnnualPDFExporter(Plantation plantation, List<Transaction> palmTransaction, List<Transaction> fertilizerTransaction, List<Transaction> trimTransaction) throws IOException {
        super(plantation, palmTransaction, fertilizerTransaction, trimTransaction);
    }

    @Override
    public void export() throws FileNotFoundException {
        document.add(new Paragraph("สวัสดี", font));

        document.close();
    }
}
