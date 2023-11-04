package ku.cs.palmoilmnger.service.summary;

import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.model.SummaryDTO;
import ku.cs.palmoilmnger.model.TransactionSummaryDTO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AnnualPDFExporter extends PDFExporter {
    private String year;

    public AnnualPDFExporter(Plantation plantation, List<TransactionSummaryDTO> palmTransaction, List<TransactionSummaryDTO> fertilizerTransaction, List<TransactionSummaryDTO> trimTransaction, SummaryDTO summaryDTO, String year) throws IOException {
        super(plantation, palmTransaction, fertilizerTransaction, trimTransaction, summaryDTO);
        this.year = year;
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
        Paragraph title = new Paragraph("สรุปผลการดำเนินงานรายปี " + year, font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        font.setSize(14);
        Paragraph title1 = new Paragraph("แปลง " + plantation.getName() + " จำนวน " + plantation.getNumOfRaiUnit() + " ไร่", font);
        title1.setAlignment(Element.ALIGN_CENTER);
        document.add(title1);

        //palm table
        font.setSize(11);
        document.add(new Paragraph("\nรายการตัดปาล์ม\n\n", font));
        PdfPTable palmTable = createTable();
        writeTableData(palmTable, palmTransaction);
        document.add(palmTable);

        //fertilize table
        font.setSize(11);
        document.add(new Paragraph("\nรายการใส่ปุ๋ย\n\n", font));
        PdfPTable ferTable = createTable();
        writeTableData(ferTable, fertilizerTransaction);
        document.add(ferTable);

        //trim table
        font.setSize(11);
        document.add(new Paragraph("\nรายการตัดแต่งทางใบ\n\n", font));
        PdfPTable trimTable = createTable();
        writeTableData(trimTable, trimTransaction);
        document.add(trimTable);

        document.add(new Paragraph("\n\n"));
        Paragraph line = new Paragraph("-".repeat(90), font);
        line.setAlignment(Element.ALIGN_CENTER);
        document.add(line);

        document.add(new Paragraph("\n"));
        font.setSize(11);
        document.add(new Paragraph(String.format("%-20s%21.2f", "รวมรายจ่าย", summaryDTO.getSumOfOutcome()) + "  บาท", font));
        document.add(new Paragraph(String.format("%-20s%21.2f", "รวมรายรับ", summaryDTO.getSumOfIncome()) + "  บาท", font));
        document.add(new Paragraph(String.format("%-20s%27.2f", "รวมน้ำหนัก", summaryDTO.getSumOfWeight()) + "  ตัน", font));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph(String.format("%-20s%25.2f", "กำไรสุทธิ", summaryDTO.getNp()) + "  บาท", font));
        document.add(new Paragraph(String.format("%-20s%27.2f", "อัตรากำไรสุทธิ", summaryDTO.getNpm()) + "  %", font));
        document.add(new Paragraph(String.format("%-20s%26.2f", "อัตราผลผลิต", summaryDTO.getProductivityRate()) + "  ตันต่อไร่", font));
        document.close();
    }
}
