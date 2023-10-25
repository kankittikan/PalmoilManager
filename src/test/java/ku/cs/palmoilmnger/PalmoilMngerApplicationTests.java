package ku.cs.palmoilmnger;

import com.lowagie.text.DocumentException;
import ku.cs.palmoilmnger.service.PlantationService;
import ku.cs.palmoilmnger.service.SummaryService;
import ku.cs.palmoilmnger.service.WorkRoundService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class PalmoilMngerApplicationTests {

    @Autowired
    PlantationService plantationService;

    @Autowired
    WorkRoundService workRoundService;

    @Autowired
    SummaryService summaryService;

    @Test
    void insert() throws DocumentException, IOException {
        summaryService.export();
    }

}
