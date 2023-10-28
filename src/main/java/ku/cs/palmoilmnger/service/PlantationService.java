package ku.cs.palmoilmnger.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.exception.PlantationException;
import ku.cs.palmoilmnger.model.PlantationDTO;
import ku.cs.palmoilmnger.repository.PlantationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantationService {

    @Autowired
    PlantationRepository plantationRepository;

    public void insertNew(PlantationDTO plantation) throws PlantationException {
        if(plantation.getName().isEmpty() || plantation.getNumOfRaiUnit().isEmpty()) throw new PlantationException("กรอกข้อมูลไม่ครบ");
        if(plantationRepository.findByName(plantation.getName()) != null) throw new PlantationException("มีชื่อแปลงในระบบแล้ว");

        int rai;
        try{
            rai = Integer.parseInt(plantation.getNumOfRaiUnit());
        }
        catch (NumberFormatException e) {
            throw new PlantationException("จำนวนไร่ต้องเป็นตัวเลข");
        }

        if(rai <= 0) throw new PlantationException("จำนวนไร่ต้องมากกว่า 0");

        Plantation record = new Plantation();
        record.setName(plantation.getName());
        record.setNumOfRaiUnit(rai);
        plantationRepository.save(record);
    }

    public Plantation getPlantation(Integer id) throws PlantationException {
        Optional<Plantation> record = plantationRepository.findById(id);
        if(record.isEmpty()) throw new PlantationException("ไม่พบแปลงในระบบ");
        return record.get();
    }

    public List<Plantation> getAllPlantation(){
        return plantationRepository.findAll();
    }

    public void deletePlantation(int id){
        plantationRepository.deleteById(id);
    }
}
