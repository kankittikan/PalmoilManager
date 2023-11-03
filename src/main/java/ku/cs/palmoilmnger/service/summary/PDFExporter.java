package ku.cs.palmoilmnger.service.summary;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.model.TransactionSummaryDTO;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public abstract class PDFExporter {

    protected final BaseFont baseFont = BaseFont.createFont("static/font/Kanit-Light.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    protected final Font font = new Font(baseFont, 11);
    protected Plantation plantation;
    protected List<TransactionSummaryDTO> palmTransaction;
    protected List<TransactionSummaryDTO> fertilizerTransaction;
    protected List<TransactionSummaryDTO> trimTransaction;
    protected Document document = new Document(PageSize.A4);


    public PDFExporter(Plantation plantation, List<TransactionSummaryDTO> palmTransaction, List<TransactionSummaryDTO> fertilizerTransaction, List<TransactionSummaryDTO> trimTransaction) throws IOException {
        this.plantation = plantation;
        this.palmTransaction = palmTransaction;
        this.fertilizerTransaction = fertilizerTransaction;
        this.trimTransaction = trimTransaction;

        PdfWriter.getInstance(document, new FileOutputStream("storage/pdf/summary.pdf"));
        FontFactory.register("static/font/Kanit-Light.ttf", "Kanit");
    }

    protected PdfPTable createTable() {
        PdfPTable table = new PdfPTable(3);
        PdfPCell cell = new PdfPCell();
        table.setWidthPercentage(80);
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setPadding(5);
        font.setSize(14);

        cell.setPhrase(new Phrase("รายการ", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("ประเภท", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("จำนวน", font));
        table.addCell(cell);

        return table;
    }

    protected void writeTableData(PdfPTable table, List<TransactionSummaryDTO> transactionList) {
        font.setSize(11);
        for(TransactionSummaryDTO transaction : transactionList) {
            table.addCell(new Phrase(transaction.getDescription(), font));
            table.addCell(new Phrase(transaction.getDescriptionType(), font));
            table.addCell(new Phrase(transaction.getValue(), font));
        }
    }
    public abstract void export() throws FileNotFoundException;
}
