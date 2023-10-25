package ku.cs.palmoilmnger.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.repository.PlantationRepository;
import ku.cs.palmoilmnger.repository.TransactionRepository;
import ku.cs.palmoilmnger.repository.WorkRoundRepository;
import ku.cs.palmoilmnger.repository.WorkTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SummaryService {
    @Autowired
    WorkRoundRepository workRoundRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    WorkTypeRepository workTypeRepository;
    @Autowired
    PlantationRepository plantationRepository;

    BaseFont baseFont = BaseFont.createFont("static/font/Kanit-Light.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    Font font = new Font(baseFont, 8, Font.NORMAL);

    public SummaryService() throws IOException {
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setPadding(5);

        cell.setPhrase(new Phrase("ประเภท", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("จำนวน", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table, List<Transaction> transactionList) {
        table.addCell(new Phrase("ขนส่ง", font));
        table.addCell(new Phrase("1200", font));
    }

    public void export() throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream("storage/quarter.pdf"));
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

        //COLLECT_PALM
        font.setSize(16);
        Paragraph collect_palm = new Paragraph("ตัดปาล์ม\n", font);
        p.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(collect_palm);

        font.setSize(14);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{2f, 2f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table, null);
        document.add(table);

        //APPLY_FERTILIZER
        font.setSize(16);
        Paragraph apply_fertilizer = new Paragraph("\nใส่ปุ๋ย\n", font);
        p.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(apply_fertilizer);

        font.setSize(14);
        PdfPTable table1 = new PdfPTable(2);
        table1.setWidthPercentage(100f);
        table1.setWidths(new float[]{2f, 2f});
        table1.setSpacingBefore(10);

        writeTableHeader(table1);
        writeTableData(table1, null);
        document.add(table1);

        //TRIM_PLANTATION
        font.setSize(16);
        Paragraph trim_plantation = new Paragraph("\nตัดแต่งทางใบ\n", font);
        p.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(trim_plantation);

        font.setSize(14);
        PdfPTable table2 = new PdfPTable(2);
        table2.setWidthPercentage(100f);
        table2.setWidths(new float[]{2f, 2f});
        table2.setSpacingBefore(10);

        writeTableHeader(table2);
        writeTableData(table2, null);
        document.add(table2);

        font.setSize(14);
        Paragraph result = new Paragraph("\nอัตราผลผลิต xx ต่อไร่", font);
        document.add(result);

        document.close();
    }
}
