package ku.cs.palmoilmnger;

import ku.cs.palmoilmnger.service.TransactionService;
import ku.cs.palmoilmnger.service.summary.AnnualPDFExporter;
import ku.cs.palmoilmnger.service.summary.PDFExporter;
import org.junit.jupiter.api.Test;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class PalmoilMngerApplicationTests {
    @Autowired
    private TransactionService transactionService;

    @Test
    void insert() throws IOException {
        PDFExporter pdfExporter = new AnnualPDFExporter(null, null, null, null, "2022");
        pdfExporter.export();
    }


}
