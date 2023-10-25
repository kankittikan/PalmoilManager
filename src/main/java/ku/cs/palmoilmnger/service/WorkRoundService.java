package ku.cs.palmoilmnger.service;

import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.repository.WorkRoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkRoundService {

    @Autowired
    WorkRoundRepository workRoundRepository;

    public void insertNew(WorkRound workRound) {
        workRoundRepository.save(workRound);
    }

    public List<WorkRound> findByPlantation(Plantation plantation) {
        return workRoundRepository.findByPlantation(plantation);
    }
}
