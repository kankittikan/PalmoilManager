package ku.cs.palmoilmnger;

import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.service.PlantationService;
import ku.cs.palmoilmnger.service.WorkRoundService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PalmoilMngerApplicationTests {

    @Autowired
    PlantationService plantationService;

    @Autowired
    WorkRoundService workRoundService;

    @Test
    void insert() {

    }

}
