package ku.cs.palmoilmnger;

import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.entity.WorkType;
import ku.cs.palmoilmnger.repository.PlantationRepository;
import ku.cs.palmoilmnger.repository.WorkTypeRepository;
import ku.cs.palmoilmnger.service.PlantationService;
import ku.cs.palmoilmnger.service.summary.SummaryService;
import ku.cs.palmoilmnger.service.WorkRoundService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Optional;

@SpringBootTest
class PalmoilMngerApplicationTests {

    @Autowired
    WorkTypeRepository workTypeRepository;

    @Test
    void insert() {
        WorkType palm = workTypeRepository.findById(1).get();
        WorkType fertilizer = workTypeRepository.findById(2).get();
        WorkType trim = workTypeRepository.findById(3).get();

        
    }
}
