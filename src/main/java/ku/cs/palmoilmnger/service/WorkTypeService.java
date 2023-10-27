package ku.cs.palmoilmnger.service;

import ku.cs.palmoilmnger.entity.WorkType;
import ku.cs.palmoilmnger.repository.WorkTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class WorkTypeService {
    @Autowired
    WorkTypeRepository workTypeRepository;

    public void createWorkType(WorkType workType){
        workTypeRepository.save(workType);
    }

    public WorkType getWorkType(String name){
        return workTypeRepository.findByName(name);
    }
}
