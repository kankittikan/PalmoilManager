package ku.cs.palmoilmnger.service.summary;

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

    public void export() throws IOException {
        PDFExporter pdfExporter = new PDFExporter(Report.QUARTER, null, null, null);
        pdfExporter.export();
    }
}
