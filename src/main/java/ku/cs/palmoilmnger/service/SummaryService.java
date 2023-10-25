package ku.cs.palmoilmnger.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import ku.cs.palmoilmnger.repository.PlantationRepository;
import ku.cs.palmoilmnger.repository.TransactionRepository;
import ku.cs.palmoilmnger.repository.WorkRoundRepository;
import ku.cs.palmoilmnger.repository.WorkTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

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
    Font font = new Font(baseFont, 10, Font.NORMAL);

    public SummaryService() throws IOException {
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GRAY);
        cell.setPadding(5);

        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ประเภทfggfdgdfg", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("จำนวน", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        table.addCell("A");
        table.addCell("B");
    }

    public void export() throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream("storage/quarter.pdf"));
        FontFactory.register("static/font/Kanit-Light.ttf", "Kanit");

        document.open();
        font.setSize(18);

        Paragraph p = new Paragraph("สรุปผลดำเนินงานรายไตรมาส", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
