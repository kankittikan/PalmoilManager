package ku.cs.palmoilmnger.service.summary;

import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.model.TransactionSummaryDTO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class QuarterPDFExporter extends PDFExporter {

    private final int quarter;

    public QuarterPDFExporter(Plantation plantation, List<TransactionSummaryDTO> palmTransaction, List<TransactionSummaryDTO> fertilizerTransaction, List<TransactionSummaryDTO> trimTransaction, int quarter) throws IOException {
        super(plantation, palmTransaction, fertilizerTransaction, trimTransaction);
        this.quarter = quarter;
    }

    @Override
    public void export() throws FileNotFoundException {
        document.open();
        //header
        font.setSize(11);
        Paragraph timeStamp = new Paragraph("ออกเมื่อ " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), font);
        timeStamp.setAlignment(Element.ALIGN_RIGHT);
        document.add(timeStamp);
        font.setSize(16);
        Paragraph title = new Paragraph("สรุปผลการดำเนินงานรายไตรมาส " + quarter, font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        font.setSize(14);
        Paragraph title1 = new Paragraph("แปลง " + plantation.getName(), font);
        document.add(title1);

        //palm table
        font.setSize(13);
        document.add(new Paragraph("\nตัดปาล์ม", font));
        PdfPTable palmTable = createTable();
        writeTableData(palmTable, palmTransaction);
        document.add(palmTable);

        document.close();
    }
}
