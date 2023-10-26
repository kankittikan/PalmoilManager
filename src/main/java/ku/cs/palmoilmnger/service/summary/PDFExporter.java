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

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public abstract class PDFExporter {

    protected final BaseFont baseFont = BaseFont.createFont("static/font/Kanit-Light.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    protected final Font font = new Font(baseFont);
    protected Report report;
    protected Plantation plantation;
    protected WorkRound workRound;
    protected List<Transaction> palmTransaction;
    protected List<Transaction> fertilizerTransaction;
    protected List<Transaction> trimTransaction;
    protected Document document = new Document(PageSize.A4);


    public PDFExporter(Report report, Plantation plantation, WorkRound workRound, List<Transaction> palmTransaction, List<Transaction> fertilizerTransaction, List<Transaction> trimTransaction) throws IOException {
        this.report = report;
        this.plantation = plantation;
        this.workRound = workRound;
        this.palmTransaction = palmTransaction;
        this.fertilizerTransaction = fertilizerTransaction;
        this.trimTransaction = trimTransaction;

        PdfWriter.getInstance(document, new FileOutputStream("storage/pdf/summary.pdf"));
        FontFactory.register("static/font/Kanit-Light.ttf", "Kanit");
    }

    protected PdfPTable createTable() {
        PdfPTable table = new PdfPTable(3);
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setPadding(5);
        font.setSize(14);

        cell.setPhrase(new Phrase("ประเภท", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("รายการ", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("จำนวน", font));
        table.addCell(cell);

        return table;
    }

    protected void writeTableData(PdfPTable table, List<Transaction> transactionList) {
        font.setSize(11);
        for(Transaction transaction : transactionList) {
            table.addCell(transaction.getDescription().getDescriptionType().getName());
            table.addCell(transaction.getDescription().getName());
            table.addCell(String.valueOf(transaction.getValue()));
        }
    }
    public abstract void export() throws FileNotFoundException;
}
