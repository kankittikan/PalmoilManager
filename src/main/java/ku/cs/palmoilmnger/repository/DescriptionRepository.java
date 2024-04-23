package ku.cs.palmoilmnger.repository;

import ku.cs.palmoilmnger.entity.Description;
import ku.cs.palmoilmnger.entity.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DescriptionRepository extends JpaRepository<Description, Integer> {
    List<Description> findByWorkType(WorkType workType);

    Description findByName(String name);
}
