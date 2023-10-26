package ku.cs.palmoilmnger;

import ku.cs.palmoilmnger.service.summary.AnnualPDFExporter;
import ku.cs.palmoilmnger.service.summary.PDFExporter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class PalmoilMngerApplicationTests {

    @Test
    void insert() throws IOException {
        PDFExporter pdfExporter = new AnnualPDFExporter(null, null, null, null, "2022");
        pdfExporter.export();
    }
}
