package ku.cs.palmoilmnger;

import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.repository.PlantationRepository;
import ku.cs.palmoilmnger.service.PlantationService;
import ku.cs.palmoilmnger.service.summary.SummaryService;
import ku.cs.palmoilmnger.service.WorkRoundService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class PalmoilMngerApplicationTests {

    @Autowired
    PlantationRepository repository;

    @Test
    void insert() throws IOException {
        repository.save(new Plantation());
    }

}
