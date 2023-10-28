package ku.cs.palmoilmnger.service;

import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.repository.PlantationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlantationService {

    @Autowired
    PlantationRepository plantationRepository;

    public void insertNew(Plantation plantation) {
        plantationRepository.save(plantation);
    }
}
