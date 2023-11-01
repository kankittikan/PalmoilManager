package ku.cs.palmoilmnger.service;

import ku.cs.palmoilmnger.entity.Description;
import ku.cs.palmoilmnger.entity.WorkType;
import ku.cs.palmoilmnger.repository.DescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DescriptionService {
    @Autowired
    DescriptionRepository descriptionRepository;

    public void createDescription(Description description){
        descriptionRepository.save(description);
    }

    public List<Description> getDescriptionsByWorkType(WorkType workType){
        return descriptionRepository.findByWorkType(workType);
    }

    public Description getDescriptionByName(String name){
        return descriptionRepository.findByName(name);
    }
}
