package ku.cs.palmoilmnger;

import ku.cs.palmoilmnger.exception.PlantationException;
import ku.cs.palmoilmnger.service.PlantationService;
import ku.cs.palmoilmnger.service.summary.SummaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class PalmoilMngerApplicationTests {
    @Autowired
    private SummaryService summaryService;
    @Autowired
    private PlantationService plantationService;

    @Test
    void insert() throws IOException, PlantationException {
        summaryService.sumUpQuarter(2023, 1, plantationService.getPlantationByName("พรุดินนา"));
    }


}
