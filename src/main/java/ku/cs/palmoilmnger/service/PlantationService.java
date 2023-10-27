package ku.cs.palmoilmnger.service;

import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.repository.PlantationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantationService {

    @Autowired
    PlantationRepository plantationRepository;

    public void insertNew(Plantation plantation) {
        plantationRepository.save(plantation);
    }

    public Plantation getPlantation(String name){
        return plantationRepository.findByName(name);
    }

    public List<Plantation> getAllPlantation(){
        return plantationRepository.findAll();
    }

    public void deletePlantation(Plantation plantation){
        plantationRepository.delete(plantation);
    }
}
