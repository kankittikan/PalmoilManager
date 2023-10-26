package ku.cs.palmoilmnger.service.summary;

import com.lowagie.text.Paragraph;
import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.entity.Transaction;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class AnnualPDFExporter extends PDFExporter{
    private String year;

    public AnnualPDFExporter(Plantation plantation, List<Transaction> palmTransaction, List<Transaction> fertilizerTransaction, List<Transaction> trimTransaction, String year) throws IOException {
        super(plantation, palmTransaction, fertilizerTransaction, trimTransaction);
        this.year = year;
    }

    @Override
    public void export() throws FileNotFoundException {

        //header
        font.setSize(16);
        Paragraph title = new Paragraph("สรุปผลการดำเนินงานปี " + year, font);
        font.setSize(14);
        Paragraph title1 = new Paragraph("แปลง " + plantation.getName(), font);
        document.add(title);
        document.add(title1);

        document.close();
    }
}
