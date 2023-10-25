package ku.cs.palmoilmnger.service.summary;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import ku.cs.palmoilmnger.common.DescriptionType;
import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.entity.WorkRound;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class PDFExporter {

    private final BaseFont baseFont = BaseFont.createFont("static/font/Kanit-Light.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    private final Font font = new Font(baseFont);
    private Report report;
    private Plantation plantation;
    private WorkRound workRound;
    private List<Transaction> palmTransaction;
    private List<Transaction> fertilizerTransaction;
    private List<Transaction> trimTransaction;

    public PDFExporter(Report report, Plantation plantation, WorkRound workRound, List<Transaction> palmTransaction, List<Transaction> fertilizerTransaction, List<Transaction> trimTransaction) throws IOException {
        this.report = report;
        this.plantation = plantation;
        this.workRound = workRound;
        this.palmTransaction = palmTransaction;
        this.fertilizerTransaction = fertilizerTransaction;
        this.trimTransaction = trimTransaction;
    }

    private PdfPTable createTable() {
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

    private void writeTableData(PdfPTable table, List<Transaction> transactionList) {
        font.setSize(11);
        for(Transaction transaction : transactionList) {
            DescriptionType descriptionType = transaction.getDescription().getDescriptionType();
            Phrase phrase;
            if(descriptionType == DescriptionType.COST_INCOME) phrase = new Phrase("รายรับ", font);
            else if(descriptionType == DescriptionType.COST_EXPENSE) phrase = new Phrase("รายจ่าย", font);
            else if(descriptionType == DescriptionType.WEIGHT) phrase = new Phrase("น้ำหนัก", font);
            else phrase = new Phrase("-", font);

            table.addCell(phrase);
            table.addCell(transaction.getDescription().getName());
            table.addCell(String.valueOf(transaction.getValue()));
        }
    }

    public void export() throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, new FileOutputStream("storage/pdf/summary.pdf"));
        FontFactory.register("static/font/Kanit-Light.ttf", "Kanit");

        document.open();

        font.setSize(8);
        Paragraph dateInfo = new Paragraph(LocalDateTime.now().toString(), font);
        dateInfo.setAlignment(Element.ALIGN_RIGHT);
        document.add(dateInfo);

        font.setSize(11);
        Paragraph info = new Paragraph("แปลง พรุดินนา\nปี 2023 ไตรมาส 2", font);
        info.setAlignment(Element.ALIGN_LEFT);
        document.add(info);

        font.setSize(18);
        Paragraph p = new Paragraph("\nสรุปผลดำเนินงานรายไตรมาส", font);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);

        document.close();
    }
}
