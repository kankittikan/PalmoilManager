package ku.cs.palmoilmnger.repository;

import ku.cs.palmoilmnger.common.WorkTypeName;
import ku.cs.palmoilmnger.entity.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkTypeRepository extends JpaRepository<WorkType, Integer> {
    WorkType findByWorkTypeName(WorkTypeName workTypeName);
}
