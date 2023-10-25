package ku.cs.palmoilmnger.service.summary;

import ku.cs.palmoilmnger.repository.PlantationRepository;
import ku.cs.palmoilmnger.repository.TransactionRepository;
import ku.cs.palmoilmnger.repository.WorkRoundRepository;
import ku.cs.palmoilmnger.repository.WorkTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void export() throws IOException {
        PDFExporter pdfExporter = new PDFExporter(Report.QUARTER, null, null, null, null, null);
        pdfExporter.export();
    }
}
